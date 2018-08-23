package org.hexworks.zircon.internal.graphics

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.builder.data.TileBuilder
import org.hexworks.zircon.api.builder.graphics.TileGraphicBuilder
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.resource.CP437Tilesets
import org.hexworks.zircon.internal.behavior.impl.DefaultBoundable
import org.junit.Before
import org.junit.Test

class DefaultLayerTest {

    lateinit var target: DefaultLayer

    @Before
    fun setUp() {
        target = DefaultLayer(
                position = OFFSET,
                backend = TILE_IMAGE)

    }

    @Test
    fun shouldProperlySetOffset() {
        val expectedOffset = Position.defaultPosition()
        target.moveTo(expectedOffset)
        assertThat(target.position())
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
        target.setTileAt(CONTAINED_POSITION, CHAR)
        assertThat(target.getTileAt(CONTAINED_POSITION).get())
                .isEqualTo(CHAR)
    }

    companion object {
        val TILESET = CP437Tilesets.CLA_18X18
        val CHAR = TileBuilder.newBuilder()
                .character('x')
                .build()
        val SIZE = Size.create(10, 10)
        val TILE_IMAGE = TileGraphicBuilder.newBuilder()
                .size(SIZE)
                .tileset(TILESET)
                .build()
        val OFFSET = Position.create(5, 5)
        val INTERSECTING_BOUNDABLE = DefaultBoundable(Size.create(6, 6))
        val NON_INTERSECTING_BOUNDABLE = DefaultBoundable(Size.create(5, 5))
        val NON_CONTAINED_BOUNDABLE = DefaultBoundable(SIZE.withRelative(Size.one()))
        val CONTAINED_POSITION = OFFSET
        val NON_CONTAINED_POSITION = CONTAINED_POSITION
                .withRelativeX(-1)
                .withRelativeY(-1)
    }

}
