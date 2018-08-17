package cn.fuser.tool.net

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

interface RequestParser<in T> {
    fun parse(o: T): Request
}

interface ResponseParser<out T> {
    fun parse(resp: Response): T
}

object NetLoader {
    private val http = OkHttpClient()

    fun <Q, R> load(q: Q, qp: RequestParser<Q>, rp: ResponseParser<R>, handleStatus: Boolean = false): R {
        val req = qp.parse(q)
        val resp = http.newCall(req).execute() ?: throw NetError("请求失败!")
        if (handleStatus && !resp.isSuccessful) throw NetError("请求异常:{}".format(resp.code()))
        return rp.parse(resp)
    }
}