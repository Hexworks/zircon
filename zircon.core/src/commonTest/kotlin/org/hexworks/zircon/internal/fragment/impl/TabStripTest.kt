package org.hexworks.zircon.internal.fragment.impl

import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import org.hexworks.zircon.api.builder.component.panel
import org.hexworks.zircon.api.builder.fragment.horizontalTabBar
import org.hexworks.zircon.api.builder.fragment.verticalTabBar
import org.hexworks.zircon.api.component.Container
import org.hexworks.zircon.api.data.Size
import kotlin.test.Test

class TabStripTest {

    // 8 tabs of width 4 in a 30-wide bar: 28 cells of strip, 7 fit, or 5 next to one scroll button (5 wide), 4 next to two.
    private fun horizontal(count: Int = 8) = horizontalTabBar {
        size = Size(30, 10)
        for (i in 0 until count) {
            tab { key = "t$i"; label = "T$i"; content = panel { preferredSize = Size(30, 7) } }
        }
    } as DefaultHorizontalTabBar

    // Tabs are 3 high; a 12-high bar has 10 cells of strip, so 3 tabs, or 2 next to a scroll button.
    private fun vertical(count: Int = 6) = verticalTabBar {
        size = Size(30, 12)
        tabWidth = 8
        for (i in 0 until count) {
            tab { key = "t$i"; label = "T$i"; content = panel { preferredSize = Size(22, 12) } }
        }
    } as DefaultVerticalTabBar

    private fun keys(bar: DefaultHorizontalTabBar) = bar.visibleTabs.map { it.key }
    private fun keys(bar: DefaultVerticalTabBar) = bar.visibleTabs.map { it.key }

    private fun stripChildren(bar: Any): Int {
        val root = (bar as org.hexworks.zircon.api.fragment.TabBar).root as Container
        return (root.children.first() as Container).children.count()
    }

    @Test
    fun everythingFitsWithoutScrollButtons() {
        val bar = horizontal(7)
        keys(bar) shouldContainExactly listOf("t0", "t1", "t2", "t3", "t4", "t5", "t6")
        stripChildren(bar) shouldBe 7
    }

    @Test
    fun overflowReservesTheLastSlotForTheAfterButton() {
        val bar = horizontal()
        keys(bar) shouldContainExactly listOf("t0", "t1", "t2", "t3", "t4")
        stripChildren(bar) shouldBe 6 // 5 tabs + "..►"
    }

    @Test
    fun selectingAHiddenTabScrollsItIntoView() {
        val bar = horizontal()
        bar.selectedKey = "t5"
        keys(bar) shouldContainExactly listOf("t2", "t3", "t4", "t5")
        stripChildren(bar) shouldBe 6 // "◄.." + 4 tabs + "..►"
        bar.tab("t5").isSelected shouldBe true
    }

    @Test
    fun reachingTheEndDropsTheAfterButton() {
        val bar = horizontal()
        bar.selectedKey = "t7"
        keys(bar) shouldContainExactly listOf("t3", "t4", "t5", "t6", "t7")
        stripChildren(bar) shouldBe 6 // "◄.." + 5 tabs
        bar.selectedKey = "t0"
        keys(bar) shouldContainExactly listOf("t0", "t1", "t2", "t3", "t4")
    }

    @Test
    fun verticalBarScrollsByTabHeight() {
        val bar = vertical()
        keys(bar) shouldContainExactly listOf("t0", "t1")
        stripChildren(bar) shouldBe 3 // 2 tabs + "▼..."
        bar.selectedKey = "t3"
        keys(bar) shouldContainExactly listOf("t3")
        stripChildren(bar) shouldBe 3 // "▲..." + 1 tab + "▼..."
        bar.selectedKey = "t5"
        keys(bar) shouldContainExactly listOf("t4", "t5")
        stripChildren(bar) shouldBe 3 // "▲..." + 2 tabs
    }

    @Test
    fun verticalBarWithFewTabsHasNoScrollButtons() {
        val bar = vertical(3)
        keys(bar) shouldContainExactly listOf("t0", "t1", "t2")
        stripChildren(bar) shouldBe 3
    }
}
