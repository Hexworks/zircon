package org.codetome.zircon.internal.util.rex

import org.codetome.zircon.api.color.TextColor
import org.codetome.zircon.internal.util.CP437Utils
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
            return REXCell(
                    character = CP437Utils.convertCp437toUnicode(buffer.int),
                    foregroundColor = TextColor.create(buffer.get().toInt() and 0xFF, buffer.get().toInt() and 0xFF, buffer.get().toInt() and 0xFF, 0),
                    backgroundColor = TextColor.create(buffer.get().toInt() and 0xFF, buffer.get().toInt() and 0xFF, buffer.get().toInt() and 0xFF, 0))
        }
    }
}
