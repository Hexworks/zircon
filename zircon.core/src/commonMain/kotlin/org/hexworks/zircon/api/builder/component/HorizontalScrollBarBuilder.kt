package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.ScrollBar
import org.hexworks.zircon.internal.component.impl.DefaultHorizontalScrollBar
import org.hexworks.zircon.internal.component.renderer.HorizontalScrollBarRenderer
import org.hexworks.zircon.internal.dsl.ZirconDsl
import kotlin.jvm.JvmStatic

/**
 * Builder for a horizontal [ScrollBar]. By default, it creates a [ScrollBar] with
 * - [itemsShownAtOnce]: `1`
 * - [numberOfScrollableItems]: `100`
 */
@ZirconDsl
class HorizontalScrollBarBuilder :
    ScrollBarBuilder<ScrollBar, HorizontalScrollBarBuilder>(HorizontalScrollBarRenderer()) {

    override fun build(): ScrollBar = DefaultHorizontalScrollBar(
        componentMetadata = createMetadata(),
        renderingStrategy = createRenderingStrategy(),
        minValue = 0,
        maxValue = numberOfScrollableItems,
        itemsShownAtOnce = size.width,
        numberOfSteps = size.width,
    ).attachListeners()

    override fun createCopy() = newBuilder()
        .withProps(props.copy())
        .withItemsShownAtOnce(itemsShownAtOnce)
        .withNumberOfScrollableItems(numberOfScrollableItems)

    companion object {

        @JvmStatic
        fun newBuilder() = HorizontalScrollBarBuilder()
    }
}
