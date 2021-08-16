package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.ScrollBar
import org.hexworks.zircon.internal.component.impl.DefaultVerticalScrollBar
import org.hexworks.zircon.internal.component.renderer.VerticalScrollBarRenderer
import org.hexworks.zircon.internal.dsl.ZirconDsl
import kotlin.jvm.JvmStatic

/**
 * Builder for a vertical [ScrollBar]. By default, it creates a [ScrollBar] with
 * - [itemsShownAtOnce]: `1`
 * - [numberOfScrollableItems]: `100`
 */
@ZirconDsl
class VerticalScrollBarBuilder :
    ScrollBarBuilder<ScrollBar, VerticalScrollBarBuilder>(VerticalScrollBarRenderer()) {

    override fun build(): ScrollBar = DefaultVerticalScrollBar(
        componentMetadata = createMetadata(),
        renderingStrategy = createRenderingStrategy(),
        minValue = 0,
        maxValue = numberOfScrollableItems,
        itemsShownAtOnce = itemsShownAtOnce,
        numberOfSteps = size.height,
    ).attachListeners()

    override fun createCopy() = newBuilder()
        .withProps(props.copy())
        .withNumberOfScrollableItems(numberOfScrollableItems)
        .withItemsShownAtOnce(itemsShownAtOnce)

    companion object {

        @JvmStatic
        fun newBuilder() = VerticalScrollBarBuilder()
    }
}
