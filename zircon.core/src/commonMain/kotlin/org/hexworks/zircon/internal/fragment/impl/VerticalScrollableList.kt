package org.hexworks.zircon.internal.fragment.impl

import org.hexworks.zircon.api.component.Label
import org.hexworks.zircon.api.component.ScrollBar
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.fragment.ScrollableList
import org.hexworks.zircon.api.graphics.Symbols
import org.hexworks.zircon.api.uievent.ComponentEventType

/**
 * This creates a vertically-scrolling list. You provide a list of [items] and a subset of them are rendered with
 * a scrollbar on the right side.
 *
 * ### Navigation
 * * To scroll by **single row**, you can click or activate the top/bottom arrows. Arrow keys while the bar is focused also
 * works.
 * * To scroll by **step** (small jumps), you can click on the empty parts above or below the bar, or use the mouse wheel.
 * * You can also click and drag the bar itself.
 *
 * ### Limitations
 * * [items] is immutable. You can't change the list after its created.
 * * [items] aren't focusable. You can click on them, but you can't tab to them.
 * * Each [item][items] can't span multiple lines. It will be clipped if it's too long.
 * * Even if [items] fully fits in [size], a scrollbar will still be displayed.
 */
class VerticalScrollableList<T>(
    private val size: Size,
    position: Position,
    override val items: List<T>,
    /** Handler for when an item in the list is activated. */
    private val onItemActivated: (item: T, idx: Int) -> Unit = { _, _ -> },
    /** Transform items in [items] into displayable strings. */
    private val renderItem: (T) -> String = { it.toString() },
    /** If set, use this instead of the default [ComponentRenderer] for the [ScrollBar] created internally. */
    private val scrollbarRenderer: ComponentRenderer<ScrollBar>? = null
) : ScrollableList<T> {
    /**
     *  Reusable list of labels we display in the main scroll panel.
     */
    private val labels = mutableListOf<Label>()

    /**
     * Index in [items] of the top item we're showing in the main scroll panel.
     */
    private var topItemIdx: Int = 0

    override val root = Components.hbox()
        .withPreferredSize(size)
        .withPosition(position)
        .withSpacing(0)
        .build()

    private val scrollPanel = Components.vbox()
        .withPreferredSize(size.withRelativeWidth(-1))
        .withDecorations()
        .withSpacing(0)
        .build()

    private val scrollBarVbox = Components.vbox()
        .withPreferredSize(size.withWidth(1))
        .withDecorations()
        .withSpacing(0)
        .build()

    private val actualScrollbar: ScrollBar = Components.verticalScrollbar()
        .withPreferredSize(1, size.height - 2)
        .withItemsShownAtOnce(size.height)
        .withNumberOfScrollableItems(items.size)
        .withDecorations()
        .also { builder ->
            scrollbarRenderer?.let { builder.withComponentRenderer(it) }
        }
        .build()

    private val decrementButton = Components.button()
        .withText("${Symbols.TRIANGLE_UP_POINTING_BLACK}")
        .withPreferredSize(1, 1)
        .withDecorations()
        .build()

    private val incrementButton = Components.button()
        .withText("${Symbols.TRIANGLE_DOWN_POINTING_BLACK}")
        .withPreferredSize(1, 1)
        .withDecorations()
        .build()

    init {
        root.addComponents(scrollPanel, scrollBarVbox)

        decrementButton.processComponentEvents(ComponentEventType.ACTIVATED) {
            actualScrollbar.decrementValues()
        }
        incrementButton.processComponentEvents(ComponentEventType.ACTIVATED) {
            actualScrollbar.incrementValues()
        }

        actualScrollbar.onValueChange {
            scrollTo(it.newValue)
        }

        scrollBarVbox.addComponents(decrementButton, actualScrollbar, incrementButton)

        displayListFromIndex()
    }

    override fun scrollTo(idx: Int) {
        topItemIdx = idx
        displayListFromIndex()
    }

    private fun displayListFromIndex() {
        val maxIdx = when {
            topItemIdx + size.height < items.size -> topItemIdx + size.height
            else -> items.size
        }
        for (idx in topItemIdx until maxIdx) {
            val labelIdx = idx - topItemIdx
            // Generate and add labels until we have enough for the current entry
            while (labelIdx > labels.lastIndex) {
                labels.add(Components.label()
                    .withDecorations()
                    .withPreferredSize(scrollPanel.contentSize.withHeight(1))
                    .build()
                    .also { label ->
                        scrollPanel.addComponent(label)
                        label.onActivated {
                            onItemActivated(items[topItemIdx + labelIdx], topItemIdx + labelIdx)
                        }
                    }
                )
            }
            labels[labelIdx].text = renderItem(items[idx])
        }
        // Clear any remaining labels, just in case
        for (labelIdx in (maxIdx - topItemIdx) until labels.size) {
            labels[labelIdx].text = ""
        }
    }
}
