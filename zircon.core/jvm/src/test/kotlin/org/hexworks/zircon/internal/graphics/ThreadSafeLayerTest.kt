package org.hexworks.zircon.internal.graphics

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.builder.data.TileBuilder
import org.hexworks.zircon.api.builder.graphics.TileGraphicsBuilder
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.internal.behavior.impl.DefaultMovable
import org.junit.Before
import org.junit.Test

@Suppress("UsePropertyAccessSyntax")
class ThreadSafeLayerTest {

    lateinit var target: ThreadSafeLayer

    @Before
    fun setUp() {
        target = ThreadSafeLayer(
                initialPosition = OFFSET,
                initialContents = TILE_IMAGE)

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
        assertThat(target.getTileAt(CONTAINED_POSITION).get())
                .isEqualTo(CHAR)
    }

    companion object {
        private val TILESET = CP437TilesetResources.cla18x18()
        val CHAR = TileBuilder.newBuilder()
                .withCharacter('x')
                .build()
        val SIZE = Size.create(10, 10)
        val TILE_IMAGE = TileGraphicsBuilder.newBuilder()
                .withSize(SIZE)
                .withTileset(TILESET)
                .build()
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
