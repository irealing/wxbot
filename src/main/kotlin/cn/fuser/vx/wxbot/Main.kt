package cn.fuser.vx.wxbot

import cn.fuser.tool.net.NetLoader

fun main(args: Array<String>) {
    val uuid = NetLoader.load(GetUUID(), WXRequestParser(), UUIDParser())
    NetLoader.load(GetQRCode(uuid), WXRequestParser(), QRCodeParser())
    val m = NetLoader.load(LoginStatus(uuid), WXRequestParser(), MapRespParser())
    println(m)
}