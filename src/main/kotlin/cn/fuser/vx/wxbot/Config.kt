package cn.fuser.vx.wxbot

object Config {
    val device = deviceID()
    private fun deviceID(): String {
        var n = (System.currentTimeMillis() / 1000).inv()
        if (n < 0) n = -n
        return String.format("e%s", n.toString())
    }

    const val httpReadTimeout = 30L
}