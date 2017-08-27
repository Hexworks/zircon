package org.codetome.zircon.internal.util.rex

import java.awt.Color
import java.nio.ByteBuffer
import java.nio.charset.Charset

/**
 * Represents a CP437 character on a REX Paint [REXLayer].
 */
data class REXCell(private val character: Char,
                   private val foregroundColor: Color,
                   private val backgroundColor: Color) {

    fun getCharacter() = character

    fun getForegroundColor() = foregroundColor

    fun getBackgroundColor() = backgroundColor

    companion object {
        private val CHARACTER_BYTES = 4

        /**
         * Factory method for [REXCell], which reads out Cell information from a [ByteBuffer].
         */
        fun fromByteBuffer(buffer: ByteBuffer): REXCell {
            val character = getCP437Char(buffer)
            val fgRed = buffer.get().toInt()
            val fgGreen = buffer.get().toInt()
            val fgBlue = buffer.get().toInt()
            val bgRed = buffer.get().toInt()
            val bgGreen = buffer.get().toInt()
            val bgBlue = buffer.get().toInt()

            return REXCell(character,
                        Color(pack(fgRed, fgGreen, fgBlue, 0)),
                        Color(pack(bgRed, bgGreen, bgBlue, 0))
            )
        }

        /**
         * Reads out a 32-bit character information from a [ByteBuffer] and returns it as a CP437 character.
         */
        private fun getCP437Char(buffer: ByteBuffer): Char {
            val b = ByteArray(CHARACTER_BYTES)
            buffer[b, 0, CHARACTER_BYTES]
            return String(b, Charset.forName("CP437"))[0]
        }

        /**
         * Encodes RGB and alpha data into a single 32-bit [Int].
         */
        private fun pack(r: Int, g: Int, b: Int, a: Int): Int {
            return ((a and 0xFF) shl 24) or
                    ((r and 0xFF) shl 16) or
                    ((g and 0xFF) shl 8) or
                    ((b and 0xFF))
        }
    }
}
