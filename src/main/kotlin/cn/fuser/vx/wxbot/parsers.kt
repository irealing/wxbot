package cn.fuser.vx.wxbot

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