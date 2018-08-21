package cn.fuser.tool.net

import org.apache.log4j.Level
import org.apache.log4j.Logger
import org.apache.log4j.Priority

/**
 * 日志工具<br>
 *     方便日志输出
 *
 * @author Memory_Leak<yuz@cnns.net>
 */
object Log : Any() {

    private val logger = Logger.getLogger(this::class.qualifiedName)
    private fun log(level: Priority, vararg args: Any) {
        if (args[0] !is String)
            return
        val fmt = args[0] as String
        val message = if (args.size < 2) fmt else String.format(fmt, *args.slice(IntRange(1, args.size - 1)).toTypedArray())
        logger.log(level, message)
    }

    private fun log(level: Priority, message: String, throwable: Throwable) = logger.log(level, message, throwable)
    fun info(vararg args: Any) = log(Level.INFO, *args)
    fun info(message: String, throwable: Throwable) = log(Level.INFO, message, throwable)
    fun debug(vararg args: Any) = log(Level.DEBUG, *args)
    fun debug(message: String, throwable: Throwable) = log(Level.DEBUG, message, throwable)
    fun warn(vararg args: Any) = log(Level.WARN, *args)
    fun warn(message: String, throwable: Throwable) = log(Level.WARN, message, throwable)
}