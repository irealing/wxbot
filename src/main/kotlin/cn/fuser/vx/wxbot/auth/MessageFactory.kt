package cn.fuser.vx.wxbot.auth

import cn.fuser.vx.wxbot.User

/**
 * 微信消息工厂
 * */
class MessageFactory private constructor(private val user: User) {
    companion object {
        fun create(user: User): MessageFactory {
            return MessageFactory(user)
        }
    }

    fun testMessage(to: String, content: String): WXMessage {
        /**
         * 创建文本消息
         * @see WXMessage
         * @param to 接收者用户名
         * @param content 内容
         * */
        return WXMessage.textMessage(user.userName, to, content)
    }
}
