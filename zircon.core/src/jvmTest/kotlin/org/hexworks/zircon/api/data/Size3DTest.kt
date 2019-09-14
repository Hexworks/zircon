package org.hexworks.zircon.api.data

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.data.impl.Position3D
import org.hexworks.zircon.api.data.impl.Size3D
import org.junit.Test

class Size3DTest {

    @Test
    fun shouldProperlyCalculateGreaterThan() {
        assertThat(Size3D.create(1, 2, 1)).isGreaterThan(Size3D.create(1, 1, 1))
    }

    @Test
    fun shouldProperlyCalculateLessThan() {
        assertThat(Size3D.create(1, 2, 1)).isLessThan(Size3D.create(1, 2, 3))
    }

    @Test
    fun shouldProperlyCalculateEqualByComparingTo() {
        assertThat(Size3D.create(1, 2, 1)).isEqualByComparingTo(Size3D.create(2, 1, 1))
    }

    @Test
    fun shouldProperlyPlus() {
        assertThat(Size3D.create(1, 2, 3).plus(Size3D.create(2, 1, 4)))
                .isEqualTo(Size3D.create(3, 3, 7))
    }

    @Test
    fun shouldProperlyMinus() {
        assertThat(Size3D.create(8, 4, 6).minus(Size3D.create(2, 1, 4)))
                .isEqualTo(Size3D.create(6, 3, 2))
    }

    @Test(expected = IllegalArgumentException::class)
    fun shouldNotCreateSizeWithNegativeLength() {
        Size3D.create(-1, 1, 1)
    }

    @Test
    fun shouldProperlyConvertToSize2D() {
        assertThat(Size3D.create(2, 3, 4).to2DSize()).isEqualTo(Size.create(2, 3))
    }

    @Test
    fun shouldFetchPositionsInProperOrder() {
        val size = Size3D.create(2, 2, 2)

        assertThat(size.fetchPositions()).containsExactly(
                Position3D.create(0, 0, 0),
                Position3D.create(1, 0, 0),
                Position3D.create(0, 1, 0),
                Position3D.create(1, 1, 0),
                Position3D.create(0, 0, 1),
                Position3D.create(1, 0, 1),
                Position3D.create(0, 1, 1),
                Position3D.create(1, 1, 1))
    }

    @Test
    fun shouldProperlyCreateFrom2DSize() {
        val size = Size.create(2, 3)

        val result = Size3D.from2DSize(size, 4)

        assertThat(result.xLength).isEqualTo(2)
        assertThat(result.yLength).isEqualTo(3)
        assertThat(result.zLength).isEqualTo(4)
    }

    @Test
    fun shouldProperlyCreateWithFactory() {
        val result = Size3D.create(2, 3, 4)

        assertThat(result.xLength).isEqualTo(2)
        assertThat(result.yLength).isEqualTo(3)
        assertThat(result.zLength).isEqualTo(4)
    }

}
