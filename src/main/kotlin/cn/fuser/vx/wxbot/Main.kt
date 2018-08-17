package cn.fuser.vx.wxbot

import cn.fuser.tool.net.NetLoader

fun main(args: Array<String>) {
    val resp = NetLoader.load(GetUUID(), WXRequestParser(), BaseTextRespParser())
    print(resp)
}