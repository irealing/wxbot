package cn.fuser.vx.wxbot.auth

import cn.fuser.vx.wxbot.*
import com.alibaba.fastjson.annotation.JSONField
import java.util.*
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

class SyncCheckRequest(authInfo: AuthInfo, synk: SyncCheckKey) : WXRequest("", Method.GET) {
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

class BaseRequest(authInfo: AuthInfo) {
    @JSONField(name = "Uin")
    val uin = authInfo.wxuin
    @JSONField(name = "Sid")
    val sid = authInfo.wxsid
    @JSONField(name = "Skey")
    val skey = authInfo.skey
    @JSONField(name = "DeviceID")
    val device = Config.device
}

data class WXSyncData(@JSONField(name = "BaseRequest") val baseRequest: BaseRequest, @JSONField(name = "SyncKey") val syncKey: SyncCheckKey) {
    @JSONField(name = "rr")
    val reverse = (System.currentTimeMillis() / 1000).inv()
}

class WXSyncRequest(authInfo: AuthInfo, syncKey: SyncCheckKey) : JSONRequest<WXSyncData>(WXSyncData(BaseRequest(authInfo), syncKey)) {
    override val uri: String = "https://%s/cgi-bin/mmwebwx-bin/webwxsync".format(authInfo.config.host)
    @WXRequestFiled("sid")
    val sid = authInfo.wxsid
    @WXRequestFiled("skey")
    val skey = authInfo.skey
}

class WXMessage(@JSONField(name = "Type") val type: Int, @JSONField(name = "Content") val content: String, @JSONField(name = "FromUserName") val from: String, @JSONField(name = "ToUserName") val to: String) {
    /**
     * 微信发送消息消息内容对象
     * */
    @JSONField(name = "ClientMsgId")
    val clientMsgID = ((System.currentTimeMillis() * 0x3e8) + (Random().nextLong() and 0x270f)).toString()
    @JSONField(name = "LocalID")
    val localID = clientMsgID

    companion object {
        fun textMessage(from: String, to: String, content: String): WXMessage {
            /**
             * 文本消息
             * @param from 发送者用户名
             * @param to 接收用户名
             * @param content 消息内容
             * */
            return WXMessage(1, content, from, to)
        }
    }
}

class SendMessageBody(authInfo: AuthInfo, @JSONField(name = "Msg") val msg: WXMessage) {
    @JSONField(name = "BaseRequest")
    val baseRequest = BaseRequest(authInfo)
    @JSONField(name = "Scene")
    val scence = 0
}

class SendMessage(authInfo: AuthInfo, msg: WXMessage) : JSONRequest<SendMessageBody>(SendMessageBody(authInfo, msg)) {
    override val uri: String = "https://%s/cgi-bin/mmwebwx-bin/webwxsendmsg".format(authInfo.config.host)
    @WXRequestFiled("lang")
    val language = "zh_CN"
    @WXRequestFiled("pass_ticket")
    val passTicket = authInfo.ticket
}