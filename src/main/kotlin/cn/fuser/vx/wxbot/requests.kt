package cn.fuser.vx.wxbot

class GetUUID : WXRequest("https://login.wx.qq.com/jslogin", Method.GET) {
    @WXRequestFiled("appid")
    val appID = "wx782c26e4c19acffb"
    @WXRequestFiled("redirect_uri")
    val redirectURI = "https://wx.qq.com/cgi-bin/mmwebwx-bin/webwxnewloginpage"
    @WXRequestFiled("fun")
    val function = "new"
    @WXRequestFiled("_")
    val timestamp = System.currentTimeMillis() / 1000
}