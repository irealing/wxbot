package cn.fuser.tool.net


import java.io.InputStream
import java.io.PrintStream
import javax.imageio.ImageIO


const val BLOCK = "MM"
const val BLACK = "  "

/**
 * 二维码输出工具
 * */
class QRPrinter(input: InputStream) {

    private val img = ImageIO.read(input)
    private fun qrString(size: Int = 37, padding: Int = 3): String {
        val builder = StringBuffer()
        val times = img.width / (size + padding * 2)
        (0..50).forEach {
            builder.append(BLACK)
        }
        builder.append("\n")
        builder.append("\n")
        val sp = padding + 1
        for (y in 0..size) {
            builder.append(BLOCK)
            for (x in 0..size) {
                val v = img.getRGB(((x + sp) * times), (y + sp) * times)
                val rgb = RGB(v)
                builder.append(if (rgb.r > 127) BLOCK else BLACK)
            }
            builder.append("\n")
        }
        (0..size + 2).forEach {
            builder.append(BLACK)
        }
        builder.append("\n")
        return builder.toString()
    }

    fun print(writer: PrintStream, size: Int = 37, padding: Int = 3) {
        writer.println(qrString(size = size, padding = padding))
        writer.flush()
    }

    private class RGB(value: Int) {
        val r = (value shr 16) and 0xff
        val g = (value shr 8) and 0xff
        val b = value and 0xff
    }
}