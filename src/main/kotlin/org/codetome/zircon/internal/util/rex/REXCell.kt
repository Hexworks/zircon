package org.codetome.zircon.internal.util.rex

import org.codetome.zircon.api.resource.CP437TilesetResource
import java.awt.Color
import java.nio.ByteBuffer

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
        /**
         * Factory method for [REXCell], which reads out Cell information from a [ByteBuffer].
         */
        fun fromByteBuffer(buffer: ByteBuffer) = REXCell(
                character = CP437TilesetResource.convertCp437toUnicode(buffer.int),
                foregroundColor = Color(pack(buffer.get().toInt(), buffer.get().toInt(), buffer.get().toInt())),
                backgroundColor = Color(pack(buffer.get().toInt(), buffer.get().toInt(), buffer.get().toInt())))

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
