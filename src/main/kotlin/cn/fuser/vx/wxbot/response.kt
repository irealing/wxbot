package cn.fuser.vx.wxbot

open class TextReply(val data: Map<String, String>) {
    protected fun int(key: String, default: Int = -1): Int = data[key]?.toInt() ?: default
    protected fun string(key: String, default: String = ""): String = data[key] ?: default
    protected fun long(key: String, default: Long): Long = data[key]?.toLong() ?: default
}

class UUIDReply(data: Map<String, String>) : TextReply(data) {
    val code = int("window.QRLogin.code")
    val uuid = string("window.QRLogin.uuid")
    val error = string("window.QRLogin.error")
    val success = code == 200
}
