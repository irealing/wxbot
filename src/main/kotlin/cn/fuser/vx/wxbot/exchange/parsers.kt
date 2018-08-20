package cn.fuser.vx.wxbot.exchange

import cn.fuser.tool.net.NetError
import cn.fuser.tool.net.NetLoader
import cn.fuser.tool.net.ResponseParser
import cn.fuser.vx.wxbot.AuthException
import cn.fuser.vx.wxbot.BaseTextRespParser
import cn.fuser.vx.wxbot.InitReply
import com.alibaba.fastjson.JSON
import okhttp3.Response
import org.apache.log4j.Logger
import java.awt.image.BufferedImage
import java.io.FileOutputStream
import java.util.regex.Pattern
import javax.imageio.ImageIO

class UUIDParser : BaseTextRespParser<UUIDReply>() {
    /**
     * 转换UUID请求结果
     * */
    override fun parse(resp: Response): UUIDReply = UUIDReply(parseMap(resp))
}

class QRCodeParser : ResponseParser<BufferedImage> {
    override fun parse(resp: Response): BufferedImage {
        return ImageIO.read(resp.body()?.byteStream())
    }
}

class ScanStatusParser : BaseTextRespParser<ScanStatus>() {
    override fun parse(resp: Response): ScanStatus = ScanStatus(parseMap(resp))
}

class LoginRespParser : ResponseParser<AuthInfo> {
    private val ticketRegex = Pattern.compile("<pass_ticket>(.+)</pass_ticket>")
    private val skeyRegex = Pattern.compile("<skey>(.+)</skey>")
    private val logger = Logger.getLogger(this::class.simpleName)
    override fun parse(resp: Response): AuthInfo {
        val body = resp.body()?.string()
        val host = resp.request().url().host() ?: ""
        logger.debug(body)
        val ticket = ticketRegex.matcher(body)
        val skey = skeyRegex.matcher(body)
        if (!ticket.find() || !skey.find()) throw NetError("error response %s".format(body))
        val cookies = NetLoader.queryCookie(resp.request().url())
        val uin = cookies.find { it.name() == "wxuin" }?.value() ?: throw AuthException("uin not found")
        val sid = cookies.find { it.name() == "wxsid" }?.value() ?: throw AuthException("sid not found")
        return AuthInfo(uin, sid, ticket.group(1), skey.group(1), config = AuthInfo.Config(host))
    }
}

class InitResponseParser : ResponseParser<InitReply> {
    override fun parse(resp: Response): InitReply {
        val text = resp.body()?.string() ?: throw NetError("empty response %s".format(resp.request().url()))
        return JSON.parseObject(text, InitReply::class.java)
    }
}

class SyncCheckParser : ResponseParser<SyncCheckRet> {
    private val regex = Regex("(retcode|selector)\\s*:\\s*\"(\\d+)\"")
    override fun parse(resp: Response): SyncCheckRet {
        val text = resp.body()?.string() ?: throw NetError("empty response %s".format(resp.request().url()))
        val ret = regex.findAll(text)
        if (ret.count() < 2) throw NetError("error response")
        val retCode = ret.find { it.groupValues[1] == "retcode" }?.groupValues?.get(2)?.toInt()
                ?: throw NetError("error response")
        val selector = ret.find { it.groupValues[1] == "selector" }?.groupValues?.get(2)?.toInt()
                ?: throw NetError("error response")
        return SyncCheckRet(retCode, selector)
    }
}
