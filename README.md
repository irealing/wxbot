WXBot

wxbot是使用Kotlin基于网页版微信开发的微信SDK。

示例

```kotlin
package cn.fuser.vx.wxbot

import cn.fuser.tool.net.QRPrinter

fun main(args: Array<String>) {
    val authRet = AuthValidator { QRPrinter(it).print(System.err) }.validate()
    val bot = WXBot(authRet)
    bot.heartbeat()
    val factory = bot.messageFactory
    val regex = Regex("^@{2}[a-z0-9]{64}$")
    val simpleMessage = SimpleMessageHandler({
        if (it.msgType == 1) {
            val content = if (regex.matches(it.fromUserName)) it.content.substringAfter("<br/>") else it.content
            bot.send(factory.textMessage(it.fromUserName, content))
        }
    })
    bot.registerHandler(simpleMessage)
}
```

author: [@Memory_Leak](mailto:irealing@163.com)

