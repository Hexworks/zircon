package org.hexworks.zircon.internal.component.impl

import io.kotest.matchers.shouldBe
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.behavior.Scrollable
import org.hexworks.zircon.api.builder.component.verticalScrollBar
import org.hexworks.zircon.api.builder.graphics.tileGraphics
import org.hexworks.zircon.api.component.ScrollBar
import org.hexworks.zircon.api.component.builder.base.decorations
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.extensions.asCharacterTileOrNull
import org.hexworks.zircon.api.graphics.Symbols
import org.hexworks.zircon.api.graphics.extensions.toDrawWindow
import org.hexworks.zircon.api.uievent.MouseEvent
import org.hexworks.zircon.api.uievent.MouseEventType.MOUSE_PRESSED
import org.hexworks.zircon.api.uievent.UIEventPhase.TARGET
import org.hexworks.zircon.internal.component.renderer.DefaultVerticalScrollBarRenderer
import kotlin.test.Test

/**
 * The thumb spans the visible share of the content and sits where the offset is; a click maps
 * the bar's content rows onto the whole scroll range, decorations or not.
 */
class DefaultVerticalScrollBarTest {

    private val scrollable = Scrollable.create(visibleSize = Size(1, 10), actualSize = Size(1, 30))

    /** The rows the thumb covers: everything that is not the bar line. */
    private fun thumbRows(bar: ScrollBar): List<Int> {
        val window = tileGraphics { size = Size(1, 10) }.toDrawWindow()
        DefaultVerticalScrollBarRenderer().render(window, ComponentRenderContext(bar as DefaultVerticalScrollBar))
        return (0 until 10).filter { y ->
            window.getTileAtOrNull(Position(0, y))?.asCharacterTileOrNull()?.character != Symbols.SINGLE_LINE_VERTICAL
        }
    }

    private fun ScrollBar.click(absolute: Position) =
        (this as DefaultVerticalScrollBar).mousePressed(MouseEvent(MOUSE_PRESSED, 1, absolute), TARGET)

    @Test
    fun the_bar_is_as_tall_as_the_visible_area_plus_its_decorations() {
        verticalScrollBar(scrollable) { }.size shouldBe Size(1, 10)
        val boxed = verticalScrollBar(scrollable) { decorations { +box() } }
        boxed.size shouldBe Size(3, 12)
        boxed.contentSize shouldBe Size(1, 10)
    }

    @Test
    fun the_thumb_spans_the_visible_share_and_follows_the_offset() {
        val bar = verticalScrollBar(scrollable) { }

        thumbRows(bar) shouldBe listOf(0, 1, 2)
        scrollable.visibleOffset = Position(0, 10)
        thumbRows(bar) shouldBe listOf(4, 5, 6)
        scrollable.visibleOffset = Position(0, 20)
        thumbRows(bar) shouldBe listOf(7, 8, 9)
    }

    @Test
    fun there_is_no_thumb_when_everything_is_visible() {
        val small = Scrollable.create(visibleSize = Size(1, 10), actualSize = Size(1, 10))
        thumbRows(verticalScrollBar(small) { }) shouldBe emptyList()
    }

    @Test
    fun a_click_maps_the_bar_onto_the_whole_scroll_range() {
        val bar = verticalScrollBar(scrollable) { position = Position(5, 5) }

        bar.click(Position(5, 5 + 9))
        scrollable.visibleOffset shouldBe Position(0, 20)
        bar.click(Position(5, 5 + 0))
        scrollable.visibleOffset shouldBe Position(0, 0)
        bar.click(Position(5, 5 + 3))
        scrollable.visibleOffset shouldBe Position(0, 7)
    }

    @Test
    fun a_click_on_a_boxed_bar_is_measured_inside_the_box() {
        val bar = verticalScrollBar(scrollable) { decorations { +box() }; position = Position(5, 5) }

        bar.click(Position(6, 6 + 9))
        scrollable.visibleOffset shouldBe Position(0, 20)
        bar.click(Position(6, 6 + 0))
        scrollable.visibleOffset shouldBe Position(0, 0)
    }
}
