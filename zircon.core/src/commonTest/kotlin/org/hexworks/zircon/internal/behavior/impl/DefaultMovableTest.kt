package org.hexworks.zircon.internal.behavior.impl

import io.kotest.matchers.shouldBe
import org.hexworks.zircon.api.builder.graphics.layer
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import kotlin.test.BeforeTest
import kotlin.test.Test

class DefaultMovableTest {

    lateinit var target: DefaultMovable

    @BeforeTest
    fun setUp() {
        target = DefaultMovable(
            size = TARGET_SIZE_10x10,
            position = Position.defaultPosition()
        )
    }

    @Test
    fun shouldContainPositionWhenThereIsNoOffsetAndSizeIsBiggerThanPos() {
        target.containsPosition(Position.defaultPosition()) shouldBe true
    }

    @Test
    fun shouldNotContainPositionWhenPositionIsOutOfBounds() {
        target.containsPosition(
            target.position
                .withRelative(Position.create(TARGET_SIZE_10x10.height, TARGET_SIZE_10x10.width))
        ) shouldBe false
    }

    @Test
    fun shouldKnowItsSizeCorrectly() {
        target.size shouldBe TARGET_SIZE_10x10
    }

    @Test
    fun shouldIntersectWhenIntersectIsCalledWithIntersectingBoundable() {
        target.intersects(DefaultMovable(TARGET_SIZE_10x10)) shouldBe true
    }

    @Test
    fun shouldNotIntersectWhenIntersectIsCalledWithNonIntersectingBoundable() {
        target.intersects(
            layer {
                size = TARGET_SIZE_10x10
                offset = NON_INTERSECTING_OFFSET_20x20
            }
        ) shouldBe false
    }

    @Test
    fun shouldIntersectWhenIntersectIsCalledWithIntersectingBoundableWithOffset() {
        target.intersects(
            layer {
                offset = INTERSECTION_OFFSET_1x1
                size = Size.one()
            }
        ) shouldBe true
    }

    @Test
    fun shouldContainBoundableWhenCalledWithContainedBoundable() {
        target.containsBoundable(DefaultMovable(Size.one())) shouldBe true
    }

    @Test
    fun shouldNotContainBoundableWhenCalledWithNonContainedBoundable() {
        target.containsBoundable(DefaultMovable(Size.create(100, 100))) shouldBe false
    }

    companion object {
        const val DEFAULT_COLS_10 = 10
        const val DEFAULT_ROWS_10 = 10
        val TARGET_SIZE_10x10 = Size.create(DEFAULT_COLS_10, DEFAULT_ROWS_10)
        val INTERSECTION_OFFSET_1x1 = Position.offset1x1()
        val NON_INTERSECTING_OFFSET_20x20 = Position.create(20, 20)
    }
}
