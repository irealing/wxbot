package cn.fuser.vx.wxbot

import cn.fuser.tool.net.NetError
import cn.fuser.tool.net.NetLoader
import cn.fuser.vx.wxbot.auth.*

class WXBot(private val authInfo: AuthInfo) {
    private val syncKey = SyncCheckKey(0, mutableListOf())
    val shutDown = false
    private val syncTask = Thread(SyncRunnable(this))

    init {
        val ret = NetLoader.load(WXInitRequest(authInfo), WXJSONReqParser(), InitResponseParser())
        ret.syncCheckKey ?: throw NetError("syncKey not found")
        syncKey.remake(ret.syncCheckKey.list)
    }

    fun checkSync(): String = NetLoader.load(SyncRequest(authInfo, syncKey), WXRequestParser(), TextRespParser())
    private class SyncRunnable(private val wxbot: WXBot) : Runnable {
        override fun run() {
            while (!wxbot.shutDown) {
                wxbot.checkSync()
            }
        }
    }

    fun startSync() {
        if (!syncTask.isAlive)
            syncTask.start()
    }
}
