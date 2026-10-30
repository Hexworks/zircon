package org.hexworks.zircon.api.builder

import io.kotest.matchers.shouldBe
import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.builder.graphics.tileGraphics
import org.hexworks.zircon.api.graphics.extensions.fill
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import kotlin.test.Test
import kotlin.test.assertFailsWith

class TileGraphicsBuilderTest {

    @Test
    fun shouldBuildProperTileGraphic() {
        val result = tileGraphics {
            size = SIZE
        }.apply {
            fill(FILLER)
        }

        result.size shouldBe SIZE

        result.getTileAtOrNull(Position(SIZE.width - 1, SIZE.height - 1)) shouldBe FILLER
    }

    @Test
    fun aTileOutsideTheSizeFailsTheBuildWhateverOrderSizeAndTilesAreSetIn() {
        assertFailsWith<IllegalArgumentException> {
            tileGraphics {
                tiles = mapOf(Position(7, 7) to FILLER)
                size = SIZE
            }
        }
        assertFailsWith<IllegalArgumentException> {
            tileGraphics {
                size = SIZE
                tiles = mapOf(Position(7, 7) to FILLER)
            }
        }
    }

    @Test
    fun tilesSetBeforeTheSizeAreKept() {
        val result = tileGraphics {
            tiles = mapOf(Position(4, 4) to FILLER)
            size = SIZE
        }

        result.getTileAtOrNull(Position(4, 4)) shouldBe FILLER
    }

    companion object {
        val FILLER = characterTile {
            character = 'a'
        }
        val SIZE = Size(5, 5)
    }
}
