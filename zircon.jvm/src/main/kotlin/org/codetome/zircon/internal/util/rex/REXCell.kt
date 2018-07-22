package org.codetome.zircon.internal.util.rex

import org.codetome.zircon.api.color.TextColor
import org.codetome.zircon.internal.util.CP437Utils
import java.awt.Color
import java.nio.ByteBuffer

/**
 * Represents a CP437 character on a REX Paint [REXLayer].
 */
data class REXCell(private val character: Char,
                   private val foregroundColor: TextColor,
                   private val backgroundColor: TextColor) {

    fun getCharacter() = character

    fun getForegroundColor() = foregroundColor

    fun getBackgroundColor() = backgroundColor

    companion object {
        /**
         * Factory method for [REXCell], which reads out Cell information from a [ByteBuffer].
         */
        fun fromByteBuffer(buffer: ByteBuffer): REXCell {
            val tc = CP437Utils.convertCp437toUnicode(buffer.int)
            val fg = Color(pack(buffer.get().toInt(), buffer.get().toInt(), buffer.get().toInt()))
            val bg = Color(pack(buffer.get().toInt(), buffer.get().toInt(), buffer.get().toInt()))
            return REXCell(
                    character = tc,
                    foregroundColor = TextColor.create(fg.red, fg.green, fg.blue, fg.alpha),
                    backgroundColor = TextColor.create(bg.red, bg.green, bg.blue, bg.alpha))
        }

        /**
         * Encodes RGB and alpha data into a single 32-bit [Int].
         */
        private fun pack(r: Int, g: Int, b: Int, a: Int = 0): Int {
            return ((a and 0xFF) shl 24) or
                    ((r and 0xFF) shl 16) or
                    ((g and 0xFF) shl 8) or
                    ((b and 0xFF))
        }
    }
}
