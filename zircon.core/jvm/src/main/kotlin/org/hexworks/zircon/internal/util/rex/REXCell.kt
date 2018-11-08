package org.hexworks.zircon.internal.util.rex

import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.internal.util.CP437Utils
import java.nio.ByteBuffer

/**
 * Represents a CP437 character on a REX Paint [REXLayer].
 */
data class REXCell(private val character: Char,
                   private val foregroundColor: TileColor,
                   private val backgroundColor: TileColor) {

    fun getCharacter() = character

    fun getForegroundColor() = foregroundColor

    fun getBackgroundColor() = backgroundColor

    companion object {
        /**
         * Factory method for [REXCell], which reads out Cell information from a [ByteBuffer].
         */
        fun fromByteBuffer(buffer: ByteBuffer): REXCell {
            return REXCell(
                    character = CP437Utils.convertCp437toUnicode(buffer.int),
                    foregroundColor = TileColor.create(buffer.get().toInt() and 0xFF, buffer.get().toInt() and 0xFF, buffer.get().toInt() and 0xFF, 255),
                    backgroundColor = TileColor.create(buffer.get().toInt() and 0xFF, buffer.get().toInt() and 0xFF, buffer.get().toInt() and 0xFF, 255))
        }
    }
}
