package cn.fuser.vx.wxbot

import cn.fuser.tool.net.NetLoader
import cn.fuser.tool.net.ResponseParser
import cn.fuser.vx.wxbot.auth.*
import org.apache.log4j.Logger

class AuthException(message: String) : Exception(message)
class AuthValidator {
    /**
     * 查询获取UUID
     * */
    private fun getUUID(): UUIDReply = NetLoader.load(GetUUID(), WXRequestParser(), UUIDParser())

    /**
     *下载二维码
     **/
    private fun getQRCode(uuid: UUIDReply): String = NetLoader.load(GetQRCode(uuid), WXRequestParser(), QRCodeParser())

    private val logger = Logger.getLogger(this::class.simpleName)
    fun validate(): AuthInfo {
        val uuid = getUUID()
        val qrFile = getQRCode(uuid)
        logger.warn("PLEASE SCAN QR-CODE FILE %s".format(qrFile))
        return waitLogin(uuid)
    }

    private fun waitLogin(uuid: UUIDReply): AuthInfo {
        while (true) {
            logger.info("waiting login ...")
            val status = lookUpScan(uuid)
            logger.info("QRCode scan result %s".format(status.code))
            if (status.success) {
                logger.info("login redirect uri %s".format(status.redirectURI))
                return login(status)
            }
            if (status.code == 400) throw AuthException("auth error 400")
        }
    }

    /**
     *查询二维码扫描结果
     * */
    private fun lookUpScan(uuid: UUIDReply): ScanStatus = NetLoader.load(LoginStatus(uuid), WXRequestParser(), ScanStatusParser())

    /**
     * 获取登录数据
     * */
    private fun login(ss: ScanStatus): AuthInfo = request(LoginRequest(ss.redirectURI), LoginRespParser())

    private inline fun <reified Q : WXRequest, P> request(q: Q, p: ResponseParser<P>): P = NetLoader.load(q, WXRequestParser(), p)
}