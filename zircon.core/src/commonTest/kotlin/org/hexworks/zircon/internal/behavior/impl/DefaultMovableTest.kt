package org.hexworks.zircon.internal.behavior.impl

import io.kotest.matchers.shouldBe
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.zircon.api.behavior.Boundable
import org.hexworks.zircon.api.builder.graphics.layer
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.extensions.withRelative
import kotlin.test.BeforeTest
import kotlin.test.Test

class DefaultMovableTest {

    lateinit var target: DefaultMovable

    @BeforeTest
    fun setUp() {
        target = DefaultMovable(
            Boundable.create(position = Position.ZERO, size = TARGET_SIZE_10x10).toProperty()
        )
    }

    @Test
    fun shouldContainPositionWhenThereIsNoOffsetAndSizeIsBiggerThanPos() {
        target.containsPosition(Position.ZERO) shouldBe true
    }

    @Test
    fun shouldNotContainPositionWhenPositionIsOutOfBounds() {
        target.containsPosition(
            target.position
                .withRelative(Position(TARGET_SIZE_10x10.height, TARGET_SIZE_10x10.width))
        ) shouldBe false
    }

    @Test
    fun shouldKnowItsSizeCorrectly() {
        target.size shouldBe TARGET_SIZE_10x10
    }

    @Test
    fun shouldIntersectWhenIntersectIsCalledWithIntersectingBoundable() {
        target.intersects(DefaultMovable(Boundable.create(size = TARGET_SIZE_10x10).toProperty())) shouldBe true
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
                size = Size.ONE
            }
        ) shouldBe true
    }

    @Test
    fun shouldContainBoundableWhenCalledWithContainedBoundable() {
        target.containsBoundable(DefaultMovable(Boundable.create(size = Size.ONE).toProperty())) shouldBe true
    }

    @Test
    fun shouldNotContainBoundableWhenCalledWithNonContainedBoundable() {
        target.containsBoundable(DefaultMovable(Boundable.create(size = Size(100, 100)).toProperty())) shouldBe false
    }

    companion object {
        const val DEFAULT_COLS_10 = 10
        const val DEFAULT_ROWS_10 = 10
        val TARGET_SIZE_10x10 = Size(DEFAULT_COLS_10, DEFAULT_ROWS_10)
        val INTERSECTION_OFFSET_1x1 = Position.OFFSET_1X1
        val NON_INTERSECTING_OFFSET_20x20 = Position(20, 20)
    }
}
