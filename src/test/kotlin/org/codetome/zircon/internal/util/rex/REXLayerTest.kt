package org.codetome.zircon.internal.util.rex

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.TextCharacter
import org.codetome.zircon.internal.color.DefaultTextColor
import org.codetome.zircon.util.rex.REXLayer
import org.junit.Test
import java.nio.ByteBuffer
import java.nio.ByteOrder

class REXLayerTest {

    @Test
    fun test() {
        val raw = arrayOf(
                // layer size
                0x02, 0x00, 0x00, 0x00, // width
                0x01, 0x00, 0x00, 0x00, // height
                // cell #1
                0x41, 0x00, 0x00, 0x00, // character (hex 41 -> dec 65 -> 'A')
                0xaa, 0xaa, 0xaa,       // foreground
                0x55, 0x55, 0x55,       // background
                // cell #2
                0x42, 0x00, 0x00, 0x00, // character (hex 42 -> dec 66 -> 'B')
                0x85, 0x85, 0x85,       // foreground
                0x23, 0x23, 0x23        // background
        )
        val ba = ByteArray(raw.size)
        for ((i, b) in raw.withIndex()) {
            ba[i] = b.toByte()
        }
        val buffer = ByteBuffer.wrap(ba)
        buffer.order(ByteOrder.LITTLE_ENDIAN)

        val layer = REXLayer.fromByteBuffer(buffer).toLayer()
        assertThat(layer.getBoundableSize()).isEqualTo(Size(2, 1))

        assertChar(
                'A',
                DefaultTextColor(85,85,85,255),
                DefaultTextColor(170,170,170,255),
                layer.getCharacterAt(Position.of(0, 0)).get()
        )
        assertChar(
                'B',
                DefaultTextColor(35,35,35,255),
                DefaultTextColor(133,133,133,255),
                layer.getCharacterAt(Position.of(1, 0)).get()
        )
    }

    private fun assertChar(expChar: Char, expBgColor: DefaultTextColor, expFgColor: DefaultTextColor, textChar: TextCharacter) {
        assertThat(textChar.getCharacter()).isEqualTo(expChar)
        assertThat(textChar.getBackgroundColor()).isEqualTo(expBgColor)
        assertThat(textChar.getForegroundColor()).isEqualTo(expFgColor)
    }
}
