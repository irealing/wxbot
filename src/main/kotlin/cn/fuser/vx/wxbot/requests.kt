package cn.fuser.vx.wxbot

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

class GetQRCode(uuid: String) : WXRequest("https://login.weixin.qq.com/qrcode/%s".format(uuid), Method.GET)
