package org.hexworks.zircon.internal.graphics

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.builder.graphics.tileGraphics
import org.hexworks.zircon.api.color.ANSITileColor.BLUE
import org.hexworks.zircon.api.color.ANSITileColor.RED
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.internal.behavior.impl.DefaultMovable
import org.junit.Before
import org.junit.Test

@Suppress("TestFunctionName")
class DefaultLayerTest {

    lateinit var target: DefaultLayer

    @Before
    fun setUp() {
        target = DefaultLayer(
            initialPosition = OFFSET,
            initialContents = EMPTY_TILE_IMAGE
        )

    }

    @Test
    fun Given_a_thread_safe_layer_When_modifying_a_tile_Then_its_state_changes() {

        val tile = Tile.defaultTile().withCharacter('x')
            .withBackgroundColor(RED)
            .withForegroundColor(BLUE)
        target.draw(tile, Position.offset1x1())

        assertThat(target.tiles.toMap()).isEqualTo(mapOf(Position.offset1x1() to tile))
    }

    @Test
    fun shouldProperlySetOffset() {
        val expectedOffset = Position.defaultPosition()
        target.moveTo(expectedOffset)
        assertThat(target.position)
            .isEqualTo(expectedOffset)
    }

    @Test
    fun shouldIntersectIntersectingBoundable() {
        assertThat(target.intersects(INTERSECTING_BOUNDABLE))
            .isTrue()
    }

    @Test
    fun shouldNotIntersectNonIntersectingBoundable() {
        assertThat(target.intersects(NON_INTERSECTING_BOUNDABLE))
            .isFalse()
    }

    @Test
    fun shouldContainContainedPosition() {
        assertThat(target.containsPosition(CONTAINED_POSITION))
            .isTrue()
    }

    @Test
    fun shouldNotContainNonContainedPosition() {
        assertThat(target.containsPosition(NON_CONTAINED_POSITION))
            .isFalse()
    }

    @Test
    fun shouldContainContainedBoundable() {
        assertThat(target.containsBoundable(target))
            .isTrue()
    }

    @Test
    fun shouldNotContainNonContainedBoundable() {
        assertThat(target.containsBoundable(NON_CONTAINED_BOUNDABLE))
            .isFalse()
    }

    @Test
    fun shouldProperlySetChar() {
        target.draw(CHAR, CONTAINED_POSITION)
        assertThat(target.getTileAtOrNull(CONTAINED_POSITION)!!)
            .isEqualTo(CHAR)
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
        val NON_CONTAINED_BOUNDABLE = DefaultMovable(SIZE.withRelative(Size.one()))
        val CONTAINED_POSITION = OFFSET
        val NON_CONTAINED_POSITION = CONTAINED_POSITION
            .withRelativeX(-1)
            .withRelativeY(-1)
    }

}
