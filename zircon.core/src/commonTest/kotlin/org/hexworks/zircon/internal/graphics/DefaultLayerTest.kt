package org.hexworks.zircon.internal.graphics

import io.kotest.matchers.shouldBe
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.builder.graphics.tileGraphics
import org.hexworks.zircon.api.color.ANSIColor
import org.hexworks.zircon.api.color.palette.ansi.DefaultAnsiPalette
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.internal.behavior.impl.DefaultMovable
import kotlin.test.BeforeTest
import kotlin.test.Test

@Suppress("TestFunctionName")
class DefaultLayerTest {

    lateinit var target: DefaultLayer

    @BeforeTest
    fun setUp() {
        target = DefaultLayer(
            initialPosition = OFFSET,
            initialContents = EMPTY_TILE_IMAGE
        )

    }

    @Test
    fun Given_a_thread_safe_layer_When_modifying_a_tile_Then_its_state_changes() {

        val tile = Tile.defaultTile().withCharacter('x')
            .withBackgroundColor(DefaultAnsiPalette[ANSIColor.RED])
            .withForegroundColor(DefaultAnsiPalette[ANSIColor.BLUE])
        target.draw(tile, Position.OFFSET_1X1)

        target.tiles.toMap() shouldBe mapOf(Position.OFFSET_1X1 to tile)
    }

    @Test
    fun shouldProperlySetOffset() {
        val expectedOffset = Position.ZERO
        target.moveTo(expectedOffset)
        target.position shouldBe expectedOffset
    }

    @Test
    fun shouldIntersectIntersectingBoundable() {
        target.intersects(INTERSECTING_BOUNDABLE) shouldBe true
    }

    @Test
    fun shouldNotIntersectNonIntersectingBoundable() {
        target.intersects(NON_INTERSECTING_BOUNDABLE) shouldBe false
    }

    @Test
    fun shouldContainContainedPosition() {
        target.containsPosition(CONTAINED_POSITION) shouldBe true
    }

    @Test
    fun shouldNotContainNonContainedPosition() {
        target.containsPosition(NON_CONTAINED_POSITION) shouldBe false
    }

    @Test
    fun shouldContainContainedBoundable() {
        target.containsBoundable(target) shouldBe true
    }

    @Test
    fun shouldNotContainNonContainedBoundable() {
        target.containsBoundable(NON_CONTAINED_BOUNDABLE) shouldBe false
    }

    @Test
    fun shouldProperlySetChar() {
        target.draw(CHAR, CONTAINED_POSITION)
        target.getTileAtOrNull(CONTAINED_POSITION)!! shouldBe CHAR
    }

    companion object {
        private val TILESET = CP437TilesetResources.cla18x18()
        val CHAR = characterTile { +'x' }
        val SIZE = Size.create(10, 10)
        val EMPTY_TILE_IMAGE = tileGraphics {
            size = SIZE
            tileset = TILESET
        }
        val OFFSET = Position.create(5, 5)
        val INTERSECTING_BOUNDABLE = DefaultMovable(Size.create(6, 6))
        val NON_INTERSECTING_BOUNDABLE = DefaultMovable(Size.create(5, 5))
        val NON_CONTAINED_BOUNDABLE = DefaultMovable(SIZE.withRelative(Size.ONE))
        val CONTAINED_POSITION = OFFSET
        val NON_CONTAINED_POSITION = CONTAINED_POSITION
            .withRelativeX(-1)
            .withRelativeY(-1)
    }

}
