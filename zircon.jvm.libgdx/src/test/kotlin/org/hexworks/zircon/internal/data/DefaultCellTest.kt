package org.hexworks.zircon.internal.data

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.data.Cell
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile
import org.junit.Test

class DefaultCellTest {

    @Test
    fun shouldReturnTheSameInstanceWhenWithPositionIsCalledWithSamePosition() {
        assertThat(TARGET.withPosition(POSITION)).isSameAs(TARGET)
    }

    @Test
    fun shouldReturnTheSameInstanceWhenWithTileIsCalledWithSameTile() {
        assertThat(TARGET.withTile(TILE)).isSameAs(TARGET)
    }

    @Test
    fun shouldProperlyCreateNewCellWithNewPosition() {
        val pos = Position.create(3, 4)

        assertThat(TARGET.withPosition(pos)).isEqualTo(Cell.create(pos, TILE))
    }

    @Test
    fun shouldProperlyCreateNewCellWithNewTile() {
        val tile = TILE.withCharacter('q')

        assertThat(TARGET.withTile(tile)).isEqualTo(Cell.create(POSITION, tile))
    }


    companion object {

        val POSITION = Position.create(1, 2)
        val TILE = Tile.defaultTile()
        val TARGET = Cell.create(POSITION, TILE)
    }
}
