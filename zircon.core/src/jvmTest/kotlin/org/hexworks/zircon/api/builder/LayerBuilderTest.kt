package org.hexworks.zircon.api.builder

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.builder.graphics.LayerBuilder
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.junit.Before
import org.junit.Test

class LayerBuilderTest {

    lateinit var target: LayerBuilder

    @Before
    fun setUp() {
        target = LayerBuilder()
    }

    @Test
    fun shouldProperlyBuildLayer() {
        val result = target.apply {
            size = SIZE
            offset = OFFSET
        }.build().apply {
            fill(FILLER)
        }

        assertThat(result.size)
            .isEqualTo(SIZE)

        assertThat(result.position)
            .isEqualTo(OFFSET)

        assertThat(result.getTileAtOrNull(OFFSET))
            .isEqualTo(FILLER)
    }

    companion object {
        val SIZE = Size.create(4, 5)
        val FILLER = Tile.defaultTile().withCharacter('x')
        val OFFSET = Position.create(3, 4)
    }
}
