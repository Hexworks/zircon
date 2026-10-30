package org.hexworks.zircon.internal.fragment.impl

import io.kotest.matchers.shouldBe
import org.hexworks.zircon.ErrorRecorder
import org.hexworks.zircon.api.builder.component.panel
import org.hexworks.zircon.api.builder.fragment.horizontalTabBar
import org.hexworks.zircon.api.data.Size
import kotlin.test.Test

class DefaultHorizontalTabBarTest {

    private fun tabBar() = horizontalTabBar {
        size = Size(30, 10)
        tab { key = "a"; label = "A"; content = panel { preferredSize = Size(30, 7) } }
        tab { key = "b"; label = "B"; content = panel { preferredSize = Size(30, 7) } }
    }

    @Test
    fun selectedKeyStartsAtTheFirstTab() {
        tabBar().selectedKey shouldBe "a"
    }

    @Test
    fun settingSelectedKeySelectsThatTab() {
        val target = tabBar() as DefaultHorizontalTabBar
        target.selectedKey = "b"
        target.tab("b").isSelected shouldBe true
        target.tab("a").isSelected shouldBe false
    }

    @Test
    fun anUnknownSelectedKeyIsReportedAndThePreviousSelectionKept() {
        val errors = ErrorRecorder()
        try {
            val target = tabBar() as DefaultHorizontalTabBar
            target.selectedKey = "nope"
            target.selectedKey shouldBe "a"
            target.tab("a").isSelected shouldBe true
            errors.sources shouldBe listOf("Tab bar")
        } finally {
            errors.uninstall()
        }
    }

    @Test
    fun selectingATabUpdatesSelectedKey() {
        val target = tabBar() as DefaultHorizontalTabBar
        var changes = listOf<String>()
        target.selectedKeyProperty.onChange { (_, newValue) -> changes = changes + newValue }
        target.tab("b").isSelected = true
        target.selectedKey shouldBe "b"
        changes shouldBe listOf("b")
    }
}
