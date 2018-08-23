package org.hexworks.zircon.internal.graphics

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.resource.CP437Tilesets
import org.hexworks.zircon.internal.shape.DefaultShape
import org.junit.Test

class DefaultShapeTest {

    @Test
    fun shouldProperlyOffsetShape() {
        assertThat(LINE_SHAPE.offsetToDefaultPosition())
                .containsExactlyInAnyOrder(TRANSFORMED_POS_0, TRANSFORMED_POS_1)
    }

    @Test
    fun shouldProperlyAddTwoShapes() {
        assertThat(LINE_SHAPE + OTHER_SHAPE).containsExactlyInAnyOrder(
                POS_0, POS_1, POS_2)
    }

    @Test(expected = IllegalArgumentException::class)
    fun shouldNotBeAbleToOffsetEmptyShape() {
        DefaultShape().offsetToDefaultPosition()
    }

    @Test
    fun shouldProperlyCreateTileGraphic() {
        val result = LINE_SHAPE.toTileGraphics(CHAR, TILESET)

        assertThat(result.getTileAt(Position.create(0, 0)).get())
                .isEqualTo(CHAR)
        assertThat(result.getTileAt(Position.create(1, 0)).get())
                .isEqualTo(Tile.empty())
        assertThat(result.getTileAt(Position.create(0, 1)).get())
                .isEqualTo(Tile.empty())
        assertThat(result.getTileAt(Position.create(1, 1)).get())
                .isEqualTo(CHAR)

    }

    companion object {
        val TILESET = CP437Tilesets.ADU_DHABI_16X16

        val POS_0 = Position.create(2, 3)
        val POS_1 = Position.create(3, 4)
        val POS_2 = Position.create(1, 2)

        val TRANSFORMED_POS_0 = Position.create(0, 0)
        val TRANSFORMED_POS_1 = Position.create(1, 1)

        val CHAR = Tile.defaultTile().withCharacter('x')

        val LINE_SHAPE = DefaultShape(setOf(POS_0, POS_1))
        val OTHER_SHAPE = DefaultShape(setOf(POS_2))
    }
}
