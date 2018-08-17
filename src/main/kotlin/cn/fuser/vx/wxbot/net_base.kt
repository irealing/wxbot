package cn.fuser.vx.wxbot

import cn.fuser.tool.net.NetError
import cn.fuser.tool.net.RequestParser
import cn.fuser.tool.net.ResponseParser
import okhttp3.FormBody
import okhttp3.HttpUrl
import okhttp3.Request
import okhttp3.Response
import kotlin.reflect.full.memberProperties

enum class Method {
    /**
     * HTTP请求方式
     * GET,POST
     * @author Memory_Leak<irealing@163.com>
     * */
    GET,
    POST
}

/**
 * 微信请求对象基类
 * @author Memory_Leak<irealing@163.com>
 * */
open class WXRequest(val uri: String, val method: Method)

/**
 * 微信请求对象字段注解
 * @author Memory_Leak<irealing@163.com>
 * */
@Target(AnnotationTarget.PROPERTY)
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
            val cfg = filed.annotations.find { it.annotationClass == WXRequestFiled::class }as? WXRequestFiled ?: continue
            ret[cfg.key] = filed.getter.call(o)?.toString() ?: ""
        }
        return ret
    }
}

/**
 * 基础文本消息转换工具
 * */
open class BaseTextRespParser : ResponseParser<Map<String, String>> {
    open override fun parse(resp: Response): Map<String, String> {
        val ret = mutableMapOf<String, String>()
        val text = resp.body()?.string() ?: throw NetError("空的返回结果:%s".format(resp.request().url().toString()))
        text.split(";").forEach {
            val i = it.indexOf("=")
            if (i < 0) return@forEach
            val k = it.substring(0, i).trim()
            var v = it.substring(i + 1).trim()
            if (v.startsWith("\"")) v = v.substring(1, v.length - 1)
            ret[k] = v
        }
        return ret
    }
}