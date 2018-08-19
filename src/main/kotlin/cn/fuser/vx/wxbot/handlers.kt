package cn.fuser.vx.wxbot

/**
 * 简单消息处理工具
 * */
class SimpleMessageHandler(private val handler: ((m: Message) -> Unit)?) : WXBot.MessageCallback {
    override fun onMessage(m: Message): Unit = handler?.invoke(m) ?: Unit
}