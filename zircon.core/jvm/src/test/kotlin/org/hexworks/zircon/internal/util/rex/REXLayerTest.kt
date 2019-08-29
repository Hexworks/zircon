package org.hexworks.zircon.internal.util.rex

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.internal.color.DefaultTileColor
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import java.nio.ByteBuffer
import java.nio.ByteOrder

class REXLayerTest {

    lateinit var target: REXLayer

    @Before
    fun setUp() {
        target = REXLayer.fromByteBuffer(createTestData())
    }

    @Test
    @Ignore
    fun shouldProperlyLoadFromByteBuffer() {
        val layer = target.toLayer(TILESET_CHEEPICUS)
        assertThat(layer.size).isEqualTo(Size.create(2, 1))

        assertChar(
                'A',
                DefaultTileColor(85, 85, 85, 255),
                DefaultTileColor(170, 170, 170, 255),
                layer.getTileAt(Position.create(0, 0)).get())
        assertChar(
                'B',
                DefaultTileColor(35, 35, 35, 255),
                DefaultTileColor(133, 133, 133, 255),
                layer.getTileAt(Position.create(1, 0)).get())
    }

    @Test
    fun shouldHaveNoFontByDefault() {
        val layer = target.toLayer(TILESET_CHEEPICUS)

        assertThat(layer.tileset).isSameAs(TILESET_CHEEPICUS)
    }

    @Test
    fun shouldHaveProperCellCount() {
        assertThat(target.getCells()).hasSize(2)
    }

    @Test
    fun shouldHaveProperWidth() {
        assertThat(target.getWidth()).isEqualTo(2)
    }

    @Test
    fun shouldHaveProperHeight() {
        assertThat(target.getHeight()).isEqualTo(1)
    }

    private fun createTestData(): ByteBuffer {
        val raw = arrayOf(
                // layer size
                0x02, 0x00, 0x00, 0x00, // xLength
                0x01, 0x00, 0x00, 0x00, // yLength
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
        return buffer
    }

    private fun assertChar(expChar: Char, expBgColor: DefaultTileColor, expFgColor: DefaultTileColor, textChar: Tile) {
        assertThat(textChar.asCharacterTile().get().character).isEqualTo(expChar)
        assertThat(textChar.backgroundColor).isEqualTo(expBgColor)
        assertThat(textChar.foregroundColor).isEqualTo(expFgColor)
    }

    companion object {

        val TILESET_CHEEPICUS = CP437TilesetResources.cheepicus16x16()
    }
}
