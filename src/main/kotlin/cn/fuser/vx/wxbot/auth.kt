package cn.fuser.vx.wxbot

import cn.fuser.tool.net.NetLoader
import cn.fuser.tool.net.ResponseParser
import cn.fuser.vx.wxbot.exchange.*
import org.apache.log4j.Logger
import java.awt.image.BufferedImage

class AuthException(message: String) : Exception(message)
/**
 * 权限校验工具
 * @constructor
 * @param handler 处理二维码回调
 * */
class AuthValidator(private val handler: (BufferedImage) -> Unit) {
    /**
     * 查询获取UUID
     * */
    private fun getUUID(): UUIDReply = NetLoader.load(GetUUID(), WXRequestParser(), UUIDParser())

    /**
     *下载二维码
     **/
    private fun getQRCode(uuid: UUIDReply): BufferedImage = NetLoader.load(GetQRCode(uuid), WXRequestParser(), QRCodeParser())

    private val logger = Logger.getLogger(this::class.simpleName)
    fun validate(): AuthInfo {
        val uuid = getUUID()
        val qr = getQRCode(uuid)
        this.handler.invoke(qr)
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
            if (status.code == 400) throw AuthException("exchange error 400")
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