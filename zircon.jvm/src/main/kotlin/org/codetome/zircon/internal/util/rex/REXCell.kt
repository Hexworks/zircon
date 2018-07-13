package org.codetome.zircon.internal.util.rex

import org.codetome.zircon.api.color.TextColor
import org.codetome.zircon.internal.multiplatform.factory.TextColorFactory
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
        fun fromByteBuffer(buffer: ByteBuffer) = REXCell(
                character = CP437Utils.convertCp437toUnicode(buffer.int),
                foregroundColor = TextColorFactory.fromRGB(buffer.get().toInt(), buffer.get().toInt(), buffer.get().toInt()),
                backgroundColor = TextColorFactory.fromRGB(buffer.get().toInt(), buffer.get().toInt(), buffer.get().toInt()))
    }
}
