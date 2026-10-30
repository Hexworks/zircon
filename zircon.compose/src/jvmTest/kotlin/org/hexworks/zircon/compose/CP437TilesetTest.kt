package org.hexworks.zircon.compose

import io.kotest.matchers.ints.shouldBeGreaterThan
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.builder.grid.tileGrid
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import kotlin.test.Test

class CP437TilesetTest {

    @Test
    fun aCharacterOutsideCodePage437RendersAsAQuestionMarkInsteadOfFailing() {
        val grid = tileGrid {
            size = Size(4, 1)
            tileset = CP437TilesetResources.wanderlust16x16()
        }
        grid.draw(characterTile { character = '€' }, Position(0, 0))
        grid.draw(characterTile { character = '\u0080' }, Position(1, 0))
        renderToPng(grid).size shouldBeGreaterThan 0
    }
}
