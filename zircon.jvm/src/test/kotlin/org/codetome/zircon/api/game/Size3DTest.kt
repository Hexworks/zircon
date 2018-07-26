package org.codetome.zircon.api.game

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.data.Position3D
import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.data.Size3D
import org.junit.Test

class Size3DTest {

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
