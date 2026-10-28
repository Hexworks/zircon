package org.hexworks.zircon.internal.graphics

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.shouldBe
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.internal.shape.DefaultShape
import kotlin.test.Test

class DefaultShapeTest {

    @Test
    fun shouldProperlyOffsetShape() {
        LINE_SHAPE.offsetToDefaultPosition() shouldContainExactlyInAnyOrder listOf(TRANSFORMED_POS_0, TRANSFORMED_POS_1)
    }

    @Test
    fun shouldProperlyAddTwoShapes() {
        (LINE_SHAPE + OTHER_SHAPE) shouldContainExactlyInAnyOrder listOf(
            POS_0, POS_1, POS_2
        )
    }

    @Test
    fun shouldNotBeAbleToOffsetEmptyShape() {
        shouldThrow<IllegalArgumentException> {
            DefaultShape().offsetToDefaultPosition()
        }
    }

    @Test
    fun shouldProperlyCreateTileGraphic() {
        val result = LINE_SHAPE.toTileGraphics(CHAR, TILESET)

        result.getTileAtOrNull(Position(0, 0)) shouldBe CHAR
        result.getTileAtOrNull(Position(1, 0)) shouldBe null
        result.getTileAtOrNull(Position(0, 1)) shouldBe null
        result.getTileAtOrNull(Position(1, 1)) shouldBe CHAR

    }

    companion object {
        val TILESET = CP437TilesetResources.aduDhabi16x16()

        val POS_0 = Position(2, 3)
        val POS_1 = Position(3, 4)
        val POS_2 = Position(1, 2)

        val TRANSFORMED_POS_0 = Position(0, 0)
        val TRANSFORMED_POS_1 = Position(1, 1)

        val CHAR = Tile.defaultTile().withCharacter('x')

        val LINE_SHAPE = DefaultShape(setOf(POS_0, POS_1))
        val OTHER_SHAPE = DefaultShape(setOf(POS_2))
    }
}
