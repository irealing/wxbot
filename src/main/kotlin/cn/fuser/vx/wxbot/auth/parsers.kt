package cn.fuser.vx.wxbot.auth

import cn.fuser.tool.net.NetError
import cn.fuser.tool.net.NetLoader
import cn.fuser.tool.net.ResponseParser
import cn.fuser.vx.wxbot.AuthException
import cn.fuser.vx.wxbot.BaseTextRespParser
import okhttp3.Response
import org.apache.log4j.Logger
import java.io.FileOutputStream
import java.util.regex.Pattern

class UUIDParser : BaseTextRespParser<UUIDReply>() {
    /**
     * 转换UUID请求结果
     * */
    override fun parse(resp: Response): UUIDReply = UUIDReply(parseMap(resp))
}

class QRCodeParser : ResponseParser<String> {
    override fun parse(resp: Response): String {
        val filename = "qr_%d.jpg".format(System.currentTimeMillis())
        val output = FileOutputStream(filename)
        output.write(resp.body()?.bytes())
        return filename
    }
}

class ScanStatusParser : BaseTextRespParser<ScanStatus>() {
    override fun parse(resp: Response): ScanStatus = ScanStatus(parseMap(resp))
}

class LoginRespParser : ResponseParser<LoginReply> {
    private val ticketRegex = Pattern.compile("<pass_ticket>(.+)</pass_ticket>")
    private val skeyRegex = Pattern.compile("<skey>(.+)</skey>")
    private val logger = Logger.getLogger(this::class.simpleName)
    override fun parse(resp: Response): LoginReply {
        val body = resp.body()?.string()
        val host = resp.request().url().host() ?: ""
        logger.debug(body)
        val ticket = ticketRegex.matcher(body)
        val skey = skeyRegex.matcher(body)
        if (!ticket.find() || !skey.find()) throw NetError("error response %s".format(body))
        val cookies = NetLoader.queryCookie(resp.request().url())
        val uin = cookies.find { it.name() == "wxuin" }?.value() ?: throw AuthException("uin not found")
        val sid = cookies.find { it.name() == "wxsid" }?.value() ?: throw AuthException("sid not found")
        return LoginReply(uin, sid, ticket.group(1), skey.group(1), config = LoginReply.Config(host))
    }
}