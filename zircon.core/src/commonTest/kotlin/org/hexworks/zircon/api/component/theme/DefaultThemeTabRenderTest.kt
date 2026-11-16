package org.hexworks.zircon.api.component.theme

import io.kotest.matchers.collections.shouldContainExactly
import org.hexworks.zircon.api.builder.component.panel
import org.hexworks.zircon.api.builder.fragment.horizontalTabBar
import org.hexworks.zircon.api.builder.fragment.verticalTabBar
import org.hexworks.zircon.api.data.Size
import kotlin.test.Test

/**
 * The default theme draws tabs the way 1.x did, now from a theme entry instead of a fragment
 * branch. One change: the strip is flush with the bar (1.x indented it by one cell along its
 * axis), because a theme entry's decorations cannot differ per orientation.
 */
class DefaultThemeTabRenderTest {

    @Test
    fun a_horizontal_tab_bar_has_the_stock_look() {
        val bar = horizontalTabBar {
            size = Size(16, 6)
            tab { key = "a"; label = "A"; content = panel { preferredSize = Size(16, 3) } }
            tab { key = "b"; label = "B"; content = panel { preferredSize = Size(16, 3) } }
        }
        // The selected tab is a flat block, the others are boxed.
        bar.root.renderToRows() shouldContainExactly listOf(
            "   ┌─┐__________",
            " A │B│__________",
            "   └─┘__________",
            "                ",
            "                ",
            "                ",
        )
    }

    @Test
    fun a_vertical_tab_bar_has_the_stock_look() {
        val bar = verticalTabBar {
            size = Size(12, 9)
            tabWidth = 5
            tab { key = "a"; label = "A"; content = panel { preferredSize = Size(7, 9) } }
            tab { key = "b"; label = "B"; content = panel { preferredSize = Size(7, 9) } }
        }
        bar.root.renderToRows() shouldContainExactly listOf(
            "            ",
            " A          ",
            "            ",
            "┌───┐       ",
            "│B  │       ",
            "└───┘       ",
            "_____       ",
            "_____       ",
            "_____       ",
        )
    }
}
