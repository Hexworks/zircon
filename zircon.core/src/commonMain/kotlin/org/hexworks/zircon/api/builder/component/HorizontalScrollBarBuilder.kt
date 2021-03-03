package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.ScrollBar
import org.hexworks.zircon.api.component.builder.base.BaseComponentBuilder
import org.hexworks.zircon.internal.dsl.ZirconDsl
import org.hexworks.zircon.internal.component.impl.DefaultHorizontalScrollBar
import org.hexworks.zircon.internal.component.renderer.HorizontalScrollBarRenderer
import kotlin.jvm.JvmStatic

@Suppress("UNCHECKED_CAST")
/**
 * Builder for the scrollbar. By default, it creates a scrollbar with a number of items of 100.
 */
@ZirconDsl
class HorizontalScrollBarBuilder(
    private var minValue: Int = 0,
    private var maxValue: Int = 100
) : BaseComponentBuilder<ScrollBar, HorizontalScrollBarBuilder>(HorizontalScrollBarRenderer()) {

    fun withNumberOfScrollableItems(items: Int) = also {
        require(items > 0) { "Number of items must be greater than 0." }
        this.maxValue = items
    }

    override fun build(): ScrollBar = DefaultHorizontalScrollBar(
        componentMetadata = createMetadata(),
        renderingStrategy = createRenderingStrategy(),
        minValue = minValue,
        maxValue = maxValue,
        itemsShownAtOnce = size.width,
        numberOfSteps = size.width,
    )

    override fun createCopy() = newBuilder().withProps(props.copy())
        .withNumberOfScrollableItems(maxValue)

    companion object {

        @JvmStatic
        fun newBuilder() = HorizontalScrollBarBuilder()
    }
}
