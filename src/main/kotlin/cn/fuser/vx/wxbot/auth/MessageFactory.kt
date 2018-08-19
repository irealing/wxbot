package cn.fuser.vx.wxbot.auth

import cn.fuser.vx.wxbot.Member
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

    fun textMessage(to: String, content: String): WXMessage {
        /**
         * 创建文本消息
         * @see WXMessage
         * @param to 接收者用户名
         * @param content 内容
         * */
        return WXMessage.textMessage(user.userName, to, content)
    }

    /**
     * 创建文本消息
     * @see textMessage
     * */
    fun textMessage(to: Member, content: String): WXMessage = textMessage(to.userName, content)
}
