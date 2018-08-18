package cn.fuser.vx.wxbot

import cn.fuser.tool.net.NetLoader

class AuthException(message: String) : Exception(message)
class AuthValidater {
    private fun getUUID(): UUIDReply = NetLoader.load(GetUUID(), WXRequestParser(), UUIDParser())
    private fun getQRCode(uuid: UUIDReply): String = NetLoader.load(GetQRCode(uuid), WXRequestParser(), QRCodeParser())
    fun validate() {
        val uuid = getUUID()
        getQRCode(uuid)
    }

    private fun waitLogin(uuid: UUIDReply) {
        while (true) {
            val status = lookUpScan(uuid)
            if (status.success) break
            if (status.code == 400) throw AuthException("auth error 400")
        }
    }

    private fun lookUpScan(uuid: UUIDReply): ScanStatus {
        return NetLoader.load(LoginStatus(uuid), WXRequestParser(), ScanStatusParser())
    }
}