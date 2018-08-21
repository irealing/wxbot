## WXBot

wxbot是使用Kotlin基于网页版微信开发的微信SDK。

### 示例

#### 发送消息

```kotlin
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

#### 转发消息

```kotlin
package cn.fuser.vx.wxbot

import cn.fuser.tool.net.Log
import cn.fuser.tool.net.QRPrinter

fun main(args: Array<String>) {
    val auth = AuthValidator({ QRPrinter(it).print(System.err) }).validate()
    val bot = WXBot(auth)
    bot.heartbeat()
	// 根据昵称查找用户
    val members = bot.contact().list.find { it.nickName.startsWith("***") }
    bot.registerHandler(SimpleMessageHandler {
        Log.info("message[%d] %s: %s", it.msgType, it.fromUserName, it.content)
        if (it.msgType != 1) return@SimpleMessageHandler
		// userName以@@开头则为微信群消息
        val content = if (it.fromUserName.startsWith("@@")) it.content.substringAfter(":<br/>") else it.content
        bot.sendText(members!!.userName, content)
    })
}
```



author: [@Memory_Leak](mailto:irealing@163.com)

