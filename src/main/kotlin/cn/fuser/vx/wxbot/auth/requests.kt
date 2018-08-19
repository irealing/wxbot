package cn.fuser.vx.wxbot.auth

import cn.fuser.vx.wxbot.*
import com.alibaba.fastjson.annotation.JSONField
import javax.xml.ws.WebFault

/**
 * 获取登录UUID请求
 * @see WXRequest
 * @see WXRequestFiled
 * */
class GetUUID : WXRequest("https://login.wx.qq.com/jslogin", Method.GET) {

    @WXRequestFiled("appid")
    val appID = "wx782c26e4c19acffb"
    @WXRequestFiled("redirect_uri")
    val redirectURI = "https://wx.qq.com/cgi-bin/mmwebwx-bin/webwxnewloginpage"
    @WXRequestFiled("fun")
    val function = "new"
    @WXRequestFiled("lang")
    val language = "zh_CN"
    @WXRequestFiled("_")
    val timestamp = System.currentTimeMillis() / 1000
}

class GetQRCode(uuid: UUIDReply) : WXRequest("https://login.weixin.qq.com/qrcode/%s".format(uuid.uuid), Method.GET)
class LoginStatus(uuid: UUIDReply) : WXRequest("https://login.wx.qq.com/cgi-bin/mmwebwx-bin/login", Method.GET) {
    /**
     * 轮询扫码登录状态
     * */
    @WXRequestFiled("loginicon")
    val loginIcon = true
    @WXRequestFiled("uuid")
    val uuid = uuid.uuid
    @WXRequestFiled("tip")
    val tip = 0
    @WXRequestFiled("_")
    val time = System.currentTimeMillis() / 1000
    @WXRequestFiled("r")
    val reverse = time.inv()
}

class WXInit(uin: String, sid: String, skey: String) {
    /**
     * 初始化微信信息请求
     * */
    class Request(@JSONField(name = "Uin") val uin: String, @JSONField(name = "Sid") val sid: String, @JSONField(name = "Skey") val skey: String) {
        @JSONField(name = "DeviceID")
        val deviceID: String = Config.device
    }

    @JSONField(name = "BaseRequest")
    val baseRequest = Request(uin, sid, skey)
}

class WXInitRequest(private val reply: AuthInfo) : JSONRequest<WXInit>("", Method.POST, WXInit(reply.wxuin, reply.wxsid, reply.skey)) {
    private val target = "https://%s/cgi-bin/mmwebwx-bin/webwxinit?r=%d&lang=zh_CN&pass_ticket=%s"
    override val uri: String
        get() = target.format(reply.config.host, (System.currentTimeMillis() / 1000).inv(), reply.ticket)
}

class LoginRequest(uri: String) : WXRequest(uri, Method.GET) {
    override val uri
        get() = "%s&fun=new".format(super.uri)
}

class SyncRequest(authInfo: AuthInfo, synk: SyncCheckKey) : WXRequest("", Method.GET) {
    private val urlFmt = "https://webpush.%s/cgi-bin/mmwebwx-bin/synccheck"
    override val uri: String = urlFmt.format(authInfo.config.host)
    @WXRequestFiled("_")
    val time = System.currentTimeMillis() / 1000
    @WXRequestFiled("r")
    val reverse = time.inv()
    @WXRequestFiled("skey")
    val skey = authInfo.skey
    @WXRequestFiled("sid")
    val sid = authInfo.wxsid
    @WXRequestFiled("uin")
    val uin = authInfo.wxuin
    @WXRequestFiled("deviceid")
    val device = Config.device
    @WXRequestFiled("synckey")
    val synckey = synk.syncKey()
}
