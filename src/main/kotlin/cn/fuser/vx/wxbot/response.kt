package cn.fuser.vx.wxbot

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

data class LoginReply(val wxuin: String, val wxsid: String)