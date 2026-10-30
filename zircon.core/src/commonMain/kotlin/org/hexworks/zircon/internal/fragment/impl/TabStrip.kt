package org.hexworks.zircon.internal.fragment.impl

import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.behavior.extensions.width
import org.hexworks.zircon.api.builder.component.button
import org.hexworks.zircon.api.component.AttachedComponent
import org.hexworks.zircon.api.component.Button
import org.hexworks.zircon.api.component.Container
import org.hexworks.zircon.api.component.builder.base.decorations
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.fragment.Tab
import org.hexworks.zircon.api.graphics.Symbols
import org.hexworks.zircon.api.uievent.MouseEventType
import org.hexworks.zircon.api.uievent.Pass
import org.hexworks.zircon.api.uievent.Processed
import org.hexworks.zircon.api.uievent.UIEventPhase

/**
 * The row or column of tabs of a tab bar. Tabs that do not fit are scrolled: a `▲…` /
 * `◄…` button takes the first slot when tabs precede the visible ones and a `▼…` / `…►`
 * button the last slot when tabs follow, each only while needed. The mouse wheel over the
 * strip scrolls one tab per notch, and selecting a tab scrolls it into view.
 *
 * [capacity] is the strip's length in cells along its axis; [sizeOf] measures a tab along
 * that axis.
 */
internal class TabStrip(
    private val strip: Container,
    private val tabs: List<Tab>,
    private val horizontal: Boolean,
    private val capacity: Int,
    scrollTabSize: Int,
    private val sizeOf: (Tab) -> Int,
) {

    private val scrollTabLength = scrollTabSize
    private val attachments = ArrayList<AttachedComponent>()
    private var firstVisible = 0

    // CP437 has no ellipsis, so the hint is dots.
    private val before: Button = scrollTab(
        text = if (horizontal) "${Symbols.TRIANGLE_LEFT_POINTING_BLACK}.." else "${Symbols.TRIANGLE_UP_POINTING_BLACK}...",
        size = if (horizontal) Size(scrollTabSize, 3) else Size(tabs.first().tabButton.width, scrollTabSize),
    ) { scrollBy(-1) }

    private val after: Button = scrollTab(
        text = if (horizontal) "..${Symbols.TRIANGLE_RIGHT_POINTING_BLACK}" else "${Symbols.TRIANGLE_DOWN_POINTING_BLACK}...",
        size = if (horizontal) Size(scrollTabSize, 3) else Size(tabs.first().tabButton.width, scrollTabSize),
    ) { scrollBy(1) }

    init {
        strip.handleMouseEvents(MouseEventType.MOUSE_WHEEL_ROTATED_DOWN) { _, phase -> onWheel(phase, 1) }
        strip.handleMouseEvents(MouseEventType.MOUSE_WHEEL_ROTATED_UP) { _, phase -> onWheel(phase, -1) }
        layout()
    }

    /** The tabs shown right now, in order. */
    val visibleTabs: List<Tab>
        get() = window(firstVisible).let { tabs.subList(it.first, it.lastExclusive) }

    fun scrollBy(delta: Int) {
        val target = (firstVisible + delta).coerceIn(0, maxFirst())
        if (target != firstVisible) {
            firstVisible = target
            layout()
        }
    }

    /** Scrolls so the tab at [index] is visible, moving as little as possible. */
    fun ensureVisible(index: Int) {
        if (index !in tabs.indices) return
        var first = firstVisible
        if (index < first) first = index
        while (index >= window(first).lastExclusive && first < maxFirst()) first++
        if (first != firstVisible) {
            firstVisible = first
            layout()
        }
    }

    private fun onWheel(phase: UIEventPhase, delta: Int) =
        if (phase == UIEventPhase.TARGET || phase == UIEventPhase.BUBBLE) {
            scrollBy(delta)
            Processed
        } else Pass

    private class Window(val first: Int, val lastExclusive: Int, val showBefore: Boolean, val showAfter: Boolean)

    /** The tabs that fit starting at [first], with the scroll buttons the ends need. */
    private fun window(first: Int): Window {
        val showBefore = first > 0
        var budget = capacity - if (showBefore) scrollTabLength else 0
        var last = first
        while (last < tabs.size && sizeOf(tabs[last]) <= budget) {
            budget -= sizeOf(tabs[last])
            last++
        }
        if (last == tabs.size) return Window(first, last, showBefore, showAfter = false)
        // Not everything fits: the last slot goes to the "after" button.
        budget = capacity - (if (showBefore) scrollTabLength else 0) - scrollTabLength
        last = first
        while (last < tabs.size && sizeOf(tabs[last]) <= budget) {
            budget -= sizeOf(tabs[last])
            last++
        }
        return Window(first, last, showBefore, showAfter = true)
    }

    /** The largest first index that still fills the strip to its end. */
    private fun maxFirst(): Int {
        for (first in 0 until tabs.size) {
            if (!window(first).showAfter) return first
        }
        return tabs.size - 1
    }

    private fun layout() {
        attachments.forEach { it.detach() }
        attachments.clear()
        val window = window(firstVisible)
        if (window.showBefore) attachments += strip.addComponent(before)
        for (i in window.first until window.lastExclusive) attachments += strip.addComponent(tabs[i].root)
        if (window.showAfter) attachments += strip.addComponent(after)
    }

    private fun scrollTab(text: String, size: Size, onClick: () -> Unit): Button = button {
        +text
        preferredSize = size
        decorations { +box() }
        onActivated { onClick() }
    }
}
