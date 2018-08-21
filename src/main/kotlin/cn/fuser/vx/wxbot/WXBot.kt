package cn.fuser.vx.wxbot

import cn.fuser.tool.net.Log
import cn.fuser.tool.net.NetError
import cn.fuser.tool.net.NetLoader
import cn.fuser.vx.wxbot.exchange.*
import com.alibaba.fastjson.JSON
import java.io.File

/**
 * 微信机器人
 * */
class WXBot(private val authInfo: AuthInfo) {
    private val syncKey: SyncCheckKey = SyncCheckKey(0, mutableListOf())
    var shutDown = false
    private val syncTask = Thread(SyncRunnable(this))
    private val user = initBot()
    private val messageCallback = mutableListOf<MessageCallback>()

    private fun initBot(): User {
        /**
         * 初始化并获取当前用户信息
         * */
        val ret = NetLoader.load(WXInitRequest(authInfo), WXJSONReqParser(), InitResponseParser())
        ret.syncCheckKey ?: throw NetError("syncKey not found")
        Log.info("remake sync-check-key")
        syncKey.remake(ret.syncCheckKey.list)
        return ret.user
    }

    private fun checkSync(): SyncCheckRet {
        val r = NetLoader.load(SyncCheckRequest(authInfo, syncKey), WXRequestParser(), SyncCheckParser())
        if (r.retCode != 0) {
            this.shutdown()
            return r
        }
        if (r.selector != 0) this.sync()
        return r
    }

    private class SyncRunnable(private val wxbot: WXBot) : Runnable {
        override fun run() {
            while (!wxbot.shutDown) {
                wxbot.checkSync()
            }
        }
    }

    /**
     * 启动同步线程
     * */
    fun heartbeat() {
        if (!syncTask.isAlive)
            syncTask.start()
    }

    private fun sync(): SyncCheckKey {
        val syncParser = JSONRespParser({
            JSON.parseObject(it, SyncRet::class.java)
        })
        val ret = NetLoader.loadJSON(WXSyncRequest(authInfo, this.syncKey), syncParser)
                ?: throw NetError("error response")
        this.syncKey.remake(ret.syncKey.list)
        ret.msgList.forEach {
            handleMessage(it)
        }
        return ret.syncKey
    }

    private fun handleMessage(m: Message) = this.messageCallback.forEach({ it.onMessage(m) })

    /**
     * 关闭后台同步线程
     * */
    fun shutdown() {
        Log.warn("SHUTDOWN")
        this.shutDown = true
    }

    /**
     * 发送文本消息
     * @param text
     * @param to
     * */
    fun sendText(to: String, text: String): SendRet {
        val msg = WXMessage(1, text, user.userName, to)
        return NetLoader.loadJSON(SendMessage(authInfo, msg), JSONRespParser { JSON.parseObject(it, SendRet::class.java) })
    }

    fun sendImg(to: String, img: File): ImgMsgRet {
        Log.info("send img(%s) to %s", img.path, to)
        val uploadReq = WXUploadFile(authInfo, img, to, user.userName)
        Log.info("upload file %s", img.path)
        val ret = NetLoader.load(uploadReq, WXRequestParser(), JSONRespParser({ JSON.parseObject(it, MediaUploadRet::class.java) }))
        if (ret.baseResponse.ret != 0) throw NetError("upload media failed %s %s".format(img.path, ret.baseResponse.ret))
        val imgMsg = SendImgMsg(user.userName, to, authInfo, ret)
        return NetLoader.loadJSON(imgMsg, JSONRespParser { JSON.parseObject(it, ImgMsgRet::class.java) })
    }

    fun contact(): Contact {
        /**
         * 获取通讯录
         * */
        val parser = JSONRespParser({ JSON.parseObject(it, Contact::class.java) })
        return NetLoader.load(QueryContact(authInfo), WXRequestParser(), parser)
    }

    /**
     * 注册Message处理Handler
     * */
    fun registerHandler(vararg callback: MessageCallback) = this.messageCallback.addAll(callback)

    /**
     * 收到消息回调
     * @see Message
     * */
    interface MessageCallback {
        fun onMessage(m: Message): Unit
    }
}
