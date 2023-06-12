package org.hexworks.zircon.api.builder

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.builder.graphics.tileGraphics
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.junit.Test

class TileGraphicsBuilderTest {

    @Test
    fun shouldBuildProperTileGraphic() {
        val result = tileGraphics {
            size = SIZE
        }.apply {
            fill(FILLER)
        }

        assertThat(result.size).isEqualTo(SIZE)

        assertThat(result.getTileAtOrNull(Position.create(SIZE.width - 1, SIZE.height - 1)))
            .isEqualTo(FILLER)
    }

    companion object {
        val FILLER = characterTile {
            character = 'a'
        }
        val SIZE = Size.create(5, 5)
    }
}
