package org.hexworks.zircon.api.builder

import io.kotest.matchers.shouldBe
import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.builder.graphics.tileGraphics
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import kotlin.test.Test

class TileGraphicsBuilderTest {

    @Test
    fun shouldBuildProperTileGraphic() {
        val result = tileGraphics {
            size = SIZE
        }.apply {
            fill(FILLER)
        }

        result.size shouldBe SIZE

        result.getTileAtOrNull(Position.create(SIZE.width - 1, SIZE.height - 1)) shouldBe FILLER
    }

    companion object {
        val FILLER = characterTile {
            character = 'a'
        }
        val SIZE = Size.create(5, 5)
    }
}
