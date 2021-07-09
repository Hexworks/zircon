package org.hexworks.zircon.api.data.base

import org.assertj.core.api.Assertions
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Rect
import org.hexworks.zircon.api.data.Size
import org.junit.Test

class BaseRectTest {

    @Test
    fun shouldSplitRect() {
        val size = Size.create(100, 100)
        val rect = Rect.create(Position.create(0, 0), size)

        val splitedRectsHorizontal = rect.splitHorizontal(50)
        val splitedRectsVertical = rect.splitVertical(80)

        Assertions.assertThat(splitedRectsHorizontal.first.width).isEqualTo(50)
        Assertions.assertThat(splitedRectsHorizontal.second.width).isEqualTo(50)
        Assertions.assertThat(splitedRectsHorizontal.second.position.x).isEqualTo(50)

        Assertions.assertThat(splitedRectsVertical.first.height).isEqualTo(80)
        Assertions.assertThat(splitedRectsVertical.second.height).isEqualTo(20)
        Assertions.assertThat(splitedRectsVertical.second.position.y).isEqualTo(80)
    }
}