package org.hexworks.zircon.internal.fragment.impl

import io.kotest.matchers.shouldBe
import org.hexworks.zircon.ErrorRecorder
import org.hexworks.zircon.api.builder.component.panel
import org.hexworks.zircon.api.builder.fragment.matrixTabBar
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.fragment.MatrixTabBar.Orientation
import kotlin.test.Test
import kotlin.test.assertFailsWith

class DefaultMatrixTabBarTest {

    // Views at (a,x) (a,y) (b,x) (c,y): b has no y, c has no x.
    private val cells = listOf("a" to "x", "a" to "y", "b" to "x", "c" to "y")
    private val views = cells.associateWith { panel { preferredSize = Size(20, 13) } }

    private fun tabBar(primary: Orientation = Orientation.HORIZONTAL) = matrixTabBar {
        size = Size(30, 16)
        tabWidth = 10
        this.primary = primary
        listOf("a", "b", "c").forEach { horizontalTab { key = it; label = it.uppercase() } }
        listOf("x", "y").forEach { verticalTab { key = it; label = it.uppercase() } }
        views.forEach { (cell, content) ->
            view { horizontal = cell.first; vertical = cell.second; this.content = content }
        }
    } as DefaultMatrixTabBar

    private fun view(h: String, v: String): Component = views.getValue(h to v)

    @Test
    fun startsAtTheFirstCell() {
        val target = tabBar()
        target.selectedHorizontalKey shouldBe "a"
        target.selectedVerticalKey shouldBe "x"
        target.shownContent shouldBe view("a", "x")
        target.visibleHorizontalTabs.map { it.key } shouldBe listOf("a", "b", "c")
        target.visibleVerticalTabs.map { it.key } shouldBe listOf("x", "y")
    }

    @Test
    fun stripsLieAroundTheContent() {
        val target = tabBar()
        target.horizontalTab("a").root.position shouldBe Position(11, 0)
        target.verticalTab("x").root.position shouldBe Position(0, 4)
        view("a", "x").position shouldBe Position(10, 3)
    }

    @Test
    fun selectingASecondaryTabShowsThatView() {
        val target = tabBar()
        target.verticalTab("y").isSelected = true
        target.selectedVerticalKey shouldBe "y"
        target.shownContent shouldBe view("a", "y")
        target.verticalTab("x").isSelected shouldBe false
    }

    @Test
    fun selectingAPrimaryTabKeepsTheSecondarySelectionWhenThatCellExists() {
        val target = tabBar()
        target.selectedHorizontalKey = "b"
        target.selectedVerticalKey shouldBe "x"
        target.shownContent shouldBe view("b", "x")
        target.visibleVerticalTabs.map { it.key } shouldBe listOf("x")
    }

    @Test
    fun selectingAPrimaryTabFallsBackToTheFirstAvailableSecondaryTab() {
        val target = tabBar()
        target.selectedHorizontalKey = "c"
        target.selectedVerticalKey shouldBe "y"
        target.shownContent shouldBe view("c", "y")
        target.visibleVerticalTabs.map { it.key } shouldBe listOf("y")
        target.verticalTab("y").isSelected shouldBe true
        target.verticalTab("x").isSelected shouldBe false
    }

    @Test
    fun theVerticalStripCanBePrimary() {
        val target = tabBar(Orientation.VERTICAL)
        target.visibleHorizontalTabs.map { it.key } shouldBe listOf("a", "b")
        target.selectedVerticalKey = "y"
        target.visibleHorizontalTabs.map { it.key } shouldBe listOf("a", "c")
        target.shownContent shouldBe view("a", "y")
        target.selectedHorizontalKey = "c"
        target.shownContent shouldBe view("c", "y")
    }

    @Test
    fun anUnknownKeyIsReportedAndThePreviousSelectionKept() {
        val errors = ErrorRecorder()
        try {
            val target = tabBar()
            target.selectedHorizontalKey = "nope"
            target.selectedHorizontalKey shouldBe "a"
            target.selectedVerticalKey = "nope"
            target.selectedVerticalKey shouldBe "x"
            target.shownContent shouldBe view("a", "x")
            errors.sources shouldBe listOf("Matrix tab bar", "Matrix tab bar")
        } finally {
            errors.uninstall()
        }
    }

    @Test
    fun aSecondaryTabWithoutAViewAtThePrimaryTabIsReportedAndThePreviousSelectionKept() {
        val errors = ErrorRecorder()
        try {
            val target = tabBar()
            target.selectedHorizontalKey = "b"
            target.selectedVerticalKey = "y"
            target.selectedVerticalKey shouldBe "x"
            target.shownContent shouldBe view("b", "x")
            errors.sources shouldBe listOf("Matrix tab bar")
        } finally {
            errors.uninstall()
        }
    }

    @Test
    fun everyTabNeedsAView() {
        assertFailsWith<IllegalArgumentException> {
            matrixTabBar {
                size = Size(30, 16)
                tabWidth = 10
                horizontalTab { key = "a"; label = "A" }
                horizontalTab { key = "b"; label = "B" }
                verticalTab { key = "x"; label = "X" }
                view { horizontal = "a"; vertical = "x"; content = panel { preferredSize = Size(20, 13) } }
            }
        }
    }
}
