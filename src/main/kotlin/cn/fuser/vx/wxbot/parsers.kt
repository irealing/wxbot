package cn.fuser.vx.wxbot

import cn.fuser.tool.net.NetLoader
import cn.fuser.tool.net.ResponseParser
import okhttp3.Response
import java.io.FileOutputStream

class UUIDParser : BaseTextRespParser<UUIDReply>() {
    /**
     * 转换UUID请求结果
     * */
    override fun parse(resp: Response): UUIDReply = UUIDReply(parseMap(resp))
}

class QRCodeParser : ResponseParser<String> {
    override fun parse(resp: Response): String {
        val filename = "%d.jpg".format(System.currentTimeMillis())
        val output = FileOutputStream(filename)
        output.write(resp.body()?.bytes())
        return filename
    }
}

class ScanStatusParser : BaseTextRespParser<ScanStatus>() {
    override fun parse(resp: Response): ScanStatus = ScanStatus(parseMap(resp))
}

class LoginRespParser : ResponseParser<LoginReply> {
    override fun parse(resp: Response): LoginReply {
        val cookies = NetLoader.queryCookie(resp.request().url())
        val uin = cookies.find { it.name() == "wxuin" }?.value() ?: throw AuthException("uin not found")
        val sid = cookies.find { it.name() == "wxsid" }?.value() ?: throw AuthException("sid not found")
        return LoginReply(uin, sid)
    }
}