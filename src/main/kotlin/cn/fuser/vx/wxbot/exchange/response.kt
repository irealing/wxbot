package cn.fuser.vx.wxbot.exchange

import cn.fuser.vx.wxbot.Member
import com.alibaba.fastjson.annotation.JSONField
import java.util.concurrent.locks.ReentrantLock

open class TextReply(private val data: Map<String, String>) {
    /**
     * 文本结果基类（application/javascript）
     * */
    protected fun int(key: String, default: Int = -1): Int = data[key]?.toInt() ?: default

    protected fun string(key: String, default: String = ""): String = data[key] ?: default
    protected fun long(key: String, default: Long): Long = data[key]?.toLong() ?: default
}

class UUIDReply(data: Map<String, String>) : TextReply(data) {
    /**
     * UUID请求结果
     * */
    val code = int("window.QRLogin.code")
    val uuid = string("window.QRLogin.uuid")
    val error = string("window.QRLogin.error")
    val success = code == 200
}

class ScanStatus(data: Map<String, String>) : TextReply(data) {
    val code = int("window.code")
    val redirectURI = string("window.redirect_uri")
    val success = code == 200
}

/**
 * 权限认证信息
 * */
data class AuthInfo(val wxuin: String, val wxsid: String, val ticket: String, val skey: String, val config: Config) {
    data class Config(val host: String)
}

/**
 * 消息同步校验码元素对象
 * */
data class SyncKey(@JSONField(name = "Key") val key: Int, @JSONField(name = "Val") val value: Long)

/**
 * SyncKey 消息同步校验码
 * */
class SyncCheckKey(@JSONField(name = "Count") var count: Int, @JSONField(name = "List") val list: MutableList<SyncKey>) {
    private val locker = ReentrantLock(true)

    fun syncKey(): String {
        /**
         * 生成校验码字符串
         * */
        locker.lock()
        val builder = StringBuilder()
        var tag = false
        this.list.forEach {
            if (tag)
                builder.append("|")
            else
                tag = true
            builder.append("%s_%s".format(it.key, it.value))
        }
        locker.unlock()
        return builder.toString()
    }

    fun remake(list: List<SyncKey>) {
        this.locker.lock()
        this.list.clear()
        this.list.addAll(list)
        this.count = this.list.size
        this.locker.unlock()
    }

    fun validate(): Boolean = !this.list.isEmpty()
    override fun toString(): String {
        return this.syncKey()
    }
}

class BaseResponse(@JSONField(name = "Ret") val ret: Int, @JSONField(name = "ErrMsg") val errMsg: String)
/**
 * 消息同步结果
 * @constructor
 * @param retCode 0/正常;
 * @param selector 0/正常;2/有新消息;
 * */
data class SyncCheckRet(val retCode: Int, val selector: Int)

/**
 * 消息发送结果对象
 * @constructor
 * @param baseResponse
 * @param msgID
 * @param localID
 * */
data class SendRet(@JSONField(name = "BaseResponse") val baseResponse: BaseResponse, @JSONField(name = "MsgID") val msgID: String, @JSONField(name = "LocalID") val localID: String)

/**
 * 通讯录
 * */
data class Contact(@JSONField(name = "BaseResponse") val baseResponse: BaseResponse, @JSONField(name = "Count") val count: Int, @JSONField(name = "MemberList") val list: List<Member>)

data class MediaUploadRet(
        @JSONField(name = "BaseResponse") val baseResponse: BaseResponse,
        @JSONField(name = "CDNThumbImgHeight") val CDNThumbImgHeight: Int,
        @JSONField(name = "CDNThumbImgWidth") val CDNThumbImgWidth: Int,
        @JSONField(name = "EncryFileName") val encryFileName: String,
        @JSONField(name = "MediaId") val mediaId: String,
        @JSONField(name = "StartPos") val startPost: Int
)