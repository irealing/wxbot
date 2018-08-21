package cn.fuser.vx.wxbot.exchange

import cn.fuser.vx.wxbot.Member
import cn.fuser.vx.wxbot.User
import java.io.File

/**
 * 微信消息工厂
 * */
class MessageFactory private constructor(private val user: User, private val auth: AuthInfo) {
    companion object {
        fun create(user: User, auth: AuthInfo): MessageFactory {
            return MessageFactory(user, auth)
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

    fun makeUploadRequest(to: String, file: File): WXUploadFile {
        return WXUploadFile(auth, file, to, user.userName)
    }

    fun makeImgMsg(to: String, uploadRet: MediaUploadRet): SendImgMsg {
        return SendImgMsg(user.userName, to, auth, uploadRet)
    }
}
