package org.codetome.zircon.graphics.layer

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.Position
import org.codetome.zircon.behavior.impl.DefaultBoundable
import org.codetome.zircon.api.TextCharacterBuilder
import org.codetome.zircon.terminal.Size
import org.junit.Before
import org.junit.Test

class DefaultLayerTest {

    lateinit var target: DefaultLayer

    @Before
    fun setUp() {
        target = DefaultLayer(
                size = SIZE,
                offset = OFFSET)
    }

    @Test
    fun shouldProperlySetOffset() {
        val expectedOffset = Position.DEFAULT_POSITION
        target.setOffset(expectedOffset)
        assertThat(target.getOffset())
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
        target.setCharacterAt(CONTAINED_POSITION, CHAR)
        assertThat(target.getCharacterAt(CONTAINED_POSITION))
                .isEqualTo(CHAR)
    }

    companion object {
        val CHAR = TextCharacterBuilder.newBuilder()
                .character('x')
                .build()
        val SIZE = Size(10, 10)
        val OFFSET = Position(5, 5)
        val INTERSECTING_BOUNDABLE = DefaultBoundable(Size(6, 6))
        val NON_INTERSECTING_BOUNDABLE = DefaultBoundable(Size(5, 5))
        val NON_CONTAINED_BOUNDABLE = DefaultBoundable(SIZE.withRelative(Size.ONE))
        val CONTAINED_POSITION = OFFSET
        val NON_CONTAINED_POSITION = CONTAINED_POSITION
                .withRelativeColumn(-1)
                .withRelativeRow(-1)
    }

}