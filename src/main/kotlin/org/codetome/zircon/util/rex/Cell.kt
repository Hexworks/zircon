package org.codetome.zircon.util.rex

import java.awt.Color
import java.nio.ByteBuffer

data class Cell(val character: Char, val foregroundColor: Color, val backgroundColor: Color) {

    companion object {
        fun fromByteBuffer(buffer: ByteBuffer): Cell {
            val character = buffer.int.toChar()
            val fgRed = buffer.get().toInt()
            val fgGreen = buffer.get().toInt()
            val fgBlue = buffer.get().toInt()
            val bgRed = buffer.get().toInt()
            val bgGreen = buffer.get().toInt()
            val bgBlue = buffer.get().toInt()

            return Cell(character,
                        Color(pack(fgRed, fgGreen, fgBlue, 0)),
                        Color(pack(bgRed, bgGreen, bgBlue, 0))
            )
        }

        private fun pack(r: Int, g: Int, b: Int, a: Int): Int {
            return ((a and 0xFF) shl 24) or
                    ((r and 0xFF) shl 16) or
                    ((g and 0xFF) shl 8) or
                    ((b and 0xFF))
        }
    }
}
