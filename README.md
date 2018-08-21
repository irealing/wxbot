WXBot

wxbot是使用Kotlin基于网页版微信开发的微信SDK。

示例

```kotlin
package cn.fuser.vx.wxbot

import cn.fuser.tool.net.QRPrinter
import java.io.File

fun main(args: Array<String>) {
    val auth = AuthValidator({ QRPrinter(it).print(System.err) }).validate()
    val bot = WXBot(auth)
    bot.heartbeat()
    val members = bot.contact().list.filter { it.userName.startsWith("@@") }
    members.forEach({
        // 发送文本消息    
        bot.sendText(it.userName, it.userName)
        // 发送图片消息
        bot.sendImg(it.userName, File("****"))
    })
}

```

author: [@Memory_Leak](mailto:irealing@163.com)

