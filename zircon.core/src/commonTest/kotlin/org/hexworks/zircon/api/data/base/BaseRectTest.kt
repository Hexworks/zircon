package org.hexworks.zircon.api.data.base

import io.kotest.matchers.shouldBe
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import kotlin.test.Test

class BaseRectTest {

    @Test
    fun shouldSplitRect() {
        val size = Size.create(100, 100)
        val rect = Boundable.create(Position.create(0, 0), size)

        val splitedRectsHorizontal = rect.splitHorizontal(50)
        val splitedRectsVertical = rect.splitVertical(80)

        splitedRectsHorizontal.first.width shouldBe 50
        splitedRectsHorizontal.second.width shouldBe 50
        splitedRectsHorizontal.second.position.x shouldBe 50

        splitedRectsVertical.first.height shouldBe 80
        splitedRectsVertical.second.height shouldBe 20
        splitedRectsVertical.second.position.y shouldBe 80
    }
}
