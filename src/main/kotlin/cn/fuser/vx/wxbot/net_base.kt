package cn.fuser.vx.wxbot

import cn.fuser.tool.net.FormatError
import cn.fuser.tool.net.NetError
import cn.fuser.tool.net.RequestParser
import cn.fuser.tool.net.ResponseParser
import com.alibaba.fastjson.JSON
import okhttp3.*
import org.apache.log4j.Logger
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.memberProperties

private const val USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36"
private const val BASE_URL = "https://wx.qq.com/"

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
open class WXRequest(open val uri: String, val method: Method)

/**
 * JSON请求对象
 * @author Memory_Leak<irealing@163.com>
 * */
open class JSONRequest<out T>(open val uri: String, val method: Method, val data: T)

/**
 * 微信请求对象字段注解
 * @author Memory_Leak<irealing@163.com>
 * */
@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class WXRequestFiled(val key: String)

class WXRequestParser<in T : WXRequest> : RequestParser<T> {


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
        builder.header("User-Agent", USER_AGENT)
        builder.header("Referer", BASE_URL)
        return builder.build()
    }

    private fun parsePOST(o: T): Request {
        val builder = Request.Builder().url(o.uri).get().header("User-Agent", USER_AGENT)
        builder.header("Referer", BASE_URL)
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
abstract class BaseTextRespParser<out T> : ResponseParser<T> {
    abstract override fun parse(resp: Response): T
    protected fun parseMap(resp: Response): Map<String, String> {
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

class MapRespParser : BaseTextRespParser<Map<String, String>>() {
    override fun parse(resp: Response): Map<String, String> = parseMap(resp)
}

class TextRespParser : ResponseParser<String> {
    private val logger = Logger.getLogger(this::class.simpleName)
    override fun parse(resp: Response): String {
        val ret = resp.body()?.string() ?: throw FormatError("empty response")
        logger.debug("text response %s".format(ret))
        return ret
    }
}

class WXJSONReqParser<in T> : RequestParser<JSONRequest<T>> {
    /**
     * 生成JSON 请求
     * */
    private val jsonType = MediaType.parse("application/json; charset=utf-8")

    override fun parse(o: JSONRequest<T>): Request {
        val url = parseURL(o)
        val builder = Request.Builder().url(url)
        builder.addHeader("User-Agent", USER_AGENT)
        builder.addHeader("Referer", BASE_URL)
        val json = JSON.toJSONString(o.data) ?: throw FormatError("failed to parse object to JSON string")
        val body = RequestBody.create(jsonType, json) ?: throw FormatError("build RequestBody error")
        return builder.post(body).build()
    }

    private fun parseURL(o: JSONRequest<T>): HttpUrl {
        val urlBuilder = HttpUrl.parse(o.uri)?.newBuilder() ?: throw NetError("error address %s ".format(o.uri))
        for (p in o::class.memberProperties) {
            val param = p.findAnnotation<WXRequestFiled>() ?: continue
            val value = p.getter.call(o)?.toString() ?: ""
            urlBuilder.addQueryParameter(param.key, value)
        }
        return urlBuilder.build()
    }
}