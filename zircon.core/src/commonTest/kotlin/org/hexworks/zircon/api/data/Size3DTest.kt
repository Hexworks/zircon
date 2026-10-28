package org.hexworks.zircon.api.data

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.comparables.shouldBeEqualComparingTo
import io.kotest.matchers.comparables.shouldBeGreaterThan
import io.kotest.matchers.comparables.shouldBeLessThan
import io.kotest.matchers.shouldBe
import org.hexworks.zircon.api.data.extensions.containsPosition
import org.hexworks.zircon.api.data.extensions.fetchPositions
import org.hexworks.zircon.api.data.extensions.minus
import org.hexworks.zircon.api.data.extensions.plus
import org.hexworks.zircon.api.data.extensions.to2DSize
import kotlin.test.Test

class Size3DTest {

    @Test
    fun shouldProperlyCalculateGreaterThan() {
        Size3D(1, 2, 1) shouldBeGreaterThan Size3D(1, 1, 1)
    }

    @Test
    fun shouldProperlyCalculateLessThan() {
        Size3D(1, 2, 1) shouldBeLessThan Size3D(1, 2, 3)
    }

    @Test
    fun shouldProperlyCalculateEqualByComparingTo() {
        Size3D(1, 2, 1) shouldBeEqualComparingTo Size3D(2, 1, 1)
    }

    @Test
    fun shouldProperlyPlus() {
        Size3D(1, 2, 3).plus(Size3D(2, 1, 4)) shouldBe Size3D(3, 3, 7)
    }

    @Test
    fun shouldProperlyMinus() {
        Size3D(8, 4, 6).minus(Size3D(2, 1, 4)) shouldBe Size3D(6, 3, 2)
    }

    @Test
    fun shouldNotCreateSizeWithNegativeLength() {
        shouldThrow<IllegalArgumentException> {
            Size3D(-1, 1, 1)
        }
    }

    @Test
    fun shouldProperlyConvertToSize2D() {
        Size3D(2, 3, 4).to2DSize() shouldBe Size(2, 3)
    }

    @Test
    fun shouldFetchPositionsInProperOrder() {
        val size = Size3D(2, 2, 2)

        size.fetchPositions().toList() shouldContainExactly listOf(
            Position3D(0, 0, 0),
            Position3D(1, 0, 0),
            Position3D(0, 1, 0),
            Position3D(1, 1, 0),
            Position3D(0, 0, 1),
            Position3D(1, 0, 1),
            Position3D(0, 1, 1),
            Position3D(1, 1, 1)
        )
    }

    @Test
    fun shouldProperlyCreateFrom2DSize() {
        val size = Size(2, 3)

        val result = Size3D.from2DSize(size, 4)

        result.xLength shouldBe 2
        result.yLength shouldBe 3
        result.zLength shouldBe 4
    }

    @Test
    fun shouldProperlyCreateWithFactory() {
        val result = Size3D(2, 3, 4)

        result.xLength shouldBe 2
        result.yLength shouldBe 3
        result.zLength shouldBe 4
    }

    @Test
    fun shouldProperlyCheckContainsPosition() {
        val size = Size3D(2, 2, 2)
        val posContained = Position3D(1, 1, 1)
        val posZero = Position3D(0, 0, 0)
        val posTooBig = Position3D(3, 3, 3)
        val posTooSmall = Position3D(-1, -1, -1)

        size.containsPosition(posContained) shouldBe true
        size.containsPosition(posZero) shouldBe true
        size.containsPosition(posTooBig) shouldBe false
        size.containsPosition(posTooSmall) shouldBe false
    }

}
