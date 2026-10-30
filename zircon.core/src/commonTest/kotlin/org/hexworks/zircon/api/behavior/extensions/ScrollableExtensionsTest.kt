package org.hexworks.zircon.api.behavior.extensions

import io.kotest.matchers.shouldBe
import org.hexworks.zircon.api.behavior.Scrollable
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import kotlin.test.Test

class ScrollableExtensionsTest {

    private val target = Scrollable.create(visibleSize = Size(10, 5), actualSize = Size(30, 20))

    @Test
    fun scrolling_past_the_start_stops_at_zero() {
        target.scrollOneUp() shouldBe Position.ZERO
        target.scrollOneLeft() shouldBe Position.ZERO
        target.scrollUpBy(7) shouldBe Position.ZERO
    }

    @Test
    fun scrolling_past_the_end_stops_where_the_visible_area_still_fits() {
        target.scrollRightBy(100) shouldBe Position(20, 0)
        target.scrollDownBy(100) shouldBe Position(20, 15)
        target.scrollOneRight() shouldBe Position(20, 15)
    }

    @Test
    fun scrolling_within_the_bounds_moves_by_the_given_amount() {
        target.scrollRightBy(3)
        target.scrollDownBy(2)
        target.scrollOneLeft()

        target.visibleOffset shouldBe Position(2, 2)
    }
}
