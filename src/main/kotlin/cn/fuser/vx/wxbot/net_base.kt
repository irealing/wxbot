package cn.fuser.vx.wxbot

import cn.fuser.tool.net.NetError
import cn.fuser.tool.net.RequestParser
import cn.fuser.tool.net.ResponseParser
import okhttp3.FormBody
import okhttp3.HttpUrl
import okhttp3.Request
import okhttp3.Response
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.memberProperties

enum class Method {
    GET, POST
}


open class WXRequest(val uri: String, val method: Method)
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class WXRequestFiled(val key: String)

class WXRequestParser<in T : WXRequest> : RequestParser<T> {
    private val userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36"
    private val baseURL = "https://wx.qq.com/"

    override fun parse(o: T): Request = when (o.method) {
        Method.GET -> parseGET(o)
        Method.POST -> parsePOST(o)
    }

    private fun parseGET(o: T): Request {
        val urlBuilder = HttpUrl.parse(o.uri)?.newBuilder() ?: throw NetError("地址异常:%s".format(o.uri))
        for (entry in parseParams(o)) {
            urlBuilder.addQueryParameter(entry.key, entry.value)
        }
        val builder = Request.Builder().url(urlBuilder.build()).get()
        builder.header("User-Agent", userAgent)
        builder.header("Referer", baseURL)
        return builder.build()
    }

    private fun parsePOST(o: T): Request {
        val builder = Request.Builder().url(o.uri).get().header("User-Agent", userAgent)
        builder.header("Referer", baseURL)
        val body = FormBody.Builder()
        for (entry in parseParams(o)) {
            body.add(entry.key, entry.value)
        }
        builder.post(body.build())
        return builder.build()
    }

    private fun parseParams(o: T): Map<String, String> {
        val ret = mutableMapOf<String, String>()
        val iter = o::class.memberProperties.iterator()
        while (iter.hasNext()) {
            val filed = iter.next()
            println(filed.name)
            val cfg = filed.findAnnotation<WXRequestFiled>() ?: continue
            ret[cfg.key] = filed.getter.call(o)?.toString() ?: ""
        }
        return ret
    }
}

class TextRespParser : ResponseParser<String> {
    override fun parse(resp: Response): String {
        return resp.body()?.string() ?: ""
    }
}