package cn.fuser.vx.wxbot

import cn.fuser.tool.net.NetLoader
import cn.fuser.tool.net.ResponseParser

class AuthException(message: String) : Exception(message)
class AuthValidater {
    private fun getUUID(): UUIDReply = NetLoader.load(GetUUID(), WXRequestParser(), UUIDParser())
    private fun getQRCode(uuid: UUIDReply): String = NetLoader.load(GetQRCode(uuid), WXRequestParser(), QRCodeParser())
    fun validate(): LoginReply {
        val uuid = getUUID()
        getQRCode(uuid)
        return waitLogin(uuid)
    }

    private fun waitLogin(uuid: UUIDReply): LoginReply {
        while (true) {
            val status = lookUpScan(uuid)
            if (status.success) return login(status)
            if (status.code == 400) throw AuthException("auth error 400")
        }
    }

    private fun lookUpScan(uuid: UUIDReply): ScanStatus {
        return NetLoader.load(LoginStatus(uuid), WXRequestParser(), ScanStatusParser())
    }

    private fun login(ss: ScanStatus): LoginReply = request(WXRequest(ss.redirectURI, Method.GET), LoginRespParser())

    private inline fun <reified Q : WXRequest, P> request(q: Q, p: ResponseParser<P>): P = NetLoader.load(q, WXRequestParser(), p)
}