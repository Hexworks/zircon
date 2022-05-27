package org.hexworks.zircon.internal.util.rex

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.resource.REXPaintResources.toREXCell
import org.junit.Ignore
import org.junit.Test
import java.awt.Color
import java.nio.ByteBuffer
import java.nio.ByteOrder

class REXCellTest {

    @Test
    @Ignore
    fun test() {
        val raw = arrayOf(
            0x41, 0x00, 0x00, 0x00, // character (hex 41 -> dec 65 -> 'A')
            0xaa, 0xaa, 0xaa,       // foreground
            0x55, 0x55, 0x55        // background
        )
        val ba = ByteArray(raw.size)
        for ((i, b) in raw.withIndex()) {
            ba[i] = b.toByte()
        }
        val buffer = ByteBuffer.wrap(ba)
        buffer.order(ByteOrder.LITTLE_ENDIAN)

        val cell = buffer.toREXCell()

        assertThat(cell.character).isEqualTo('A')
        assertThat(cell.foregroundColor).isEqualTo(Color(170, 170, 170))
        assertThat(cell.backgroundColor).isEqualTo(Color(85, 85, 85))
    }
}
