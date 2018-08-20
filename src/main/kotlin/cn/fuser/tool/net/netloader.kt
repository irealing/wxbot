package cn.fuser.tool.net

import cn.fuser.vx.wxbot.Config
import cn.fuser.vx.wxbot.JSONRequest
import cn.fuser.vx.wxbot.JSONRespParser
import cn.fuser.vx.wxbot.WXJSONReqParser
import okhttp3.*
import org.apache.log4j.Logger
import java.net.CookieManager
import java.net.CookiePolicy
import java.util.*
import java.util.concurrent.TimeUnit

interface RequestParser<in T> {
    /**
     * HTTP请求Parser转换对象为Request
     * */
    fun parse(o: T): Request
}

interface ResponseParser<out T> {
    /**
     * 转换请求结果为对象
     * */
    fun parse(resp: Response): T
}

object NetLoader {
    /**
     * HTTP请求工具
     * */
    private const val readTimeout: Long = Config.httpReadTimeout
    private val http = buildClient()
    private val logger = Logger.getLogger(this::class.simpleName)
    private fun buildClient(): OkHttpClient {
        val manager = CookieManager()
        manager.setCookiePolicy(CookiePolicy.ACCEPT_ALL)
        return OkHttpClient.Builder().cookieJar(JavaNetCookieJar(manager)).readTimeout(readTimeout, TimeUnit.SECONDS).build()
    }

    fun <Q, R> load(q: Q, qp: RequestParser<Q>, rp: ResponseParser<R>, handleStatus: Boolean = false): R {
        val req = qp.parse(q)
        logger.info("request %s".format(req.url().toString()))
        val resp = http.newCall(req).execute() ?: throw NetError("请求失败!")
        if (handleStatus && !resp.isSuccessful) throw NetError("请求异常:{}".format(resp.code()))
        val ret = rp.parse(resp)
        resp.close()
        return ret
    }

    fun queryCookie(url: String): List<Cookie> {
        val uri = HttpUrl.parse(url) ?: return Collections.emptyList<Cookie>()
        return queryCookie(uri)
    }

    fun queryCookie(url: HttpUrl): List<Cookie> = http.cookieJar().loadForRequest(url)
    fun <Q, R> loadJSON(req: JSONRequest<Q>, parser: JSONRespParser<R>): R = load(req, WXJSONReqParser(), parser)
}