package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.ScrollBar
import org.hexworks.zircon.api.component.builder.base.BaseComponentBuilder
import org.hexworks.zircon.api.component.data.CommonComponentProperties
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.internal.component.renderer.DefaultComponentRenderingStrategy
import org.hexworks.zircon.internal.component.impl.DefaultVerticalScrollBar
import org.hexworks.zircon.internal.component.renderer.VerticalScrollBarRenderer
import kotlin.jvm.JvmStatic

@Suppress("UNCHECKED_CAST")
/**
 * Builder for the scrollbar. By default, it creates a scrollbar with a number of items of 100.
 */
data class VerticalScrollBarBuilder(
            private var minValue: Int = 0,
            private var maxValue: Int = 100,
            override var props: CommonComponentProperties<ScrollBar> = CommonComponentProperties(
                    componentRenderer = VerticalScrollBarRenderer()))
        : BaseComponentBuilder<ScrollBar, VerticalScrollBarBuilder>() {

    fun withNumberOfScrollableItems(items: Int) = also {
        require(items > 0) { "Number of items must be greater than 0." }
        this.maxValue = items
    }

    override fun build(): ScrollBar = DefaultVerticalScrollBar(
            componentMetadata = ComponentMetadata(
                    size = size,
                    relativePosition = position,
                    componentStyleSet = componentStyleSet,
                    tileset = tileset),
            minValue = minValue,
            maxValue = maxValue,
            itemsShownAtOnce = size.height,
            numberOfSteps = size.height,
            renderingStrategy = DefaultComponentRenderingStrategy(
                    decorationRenderers = decorationRenderers,
                    componentRenderer = props.componentRenderer as ComponentRenderer<ScrollBar>))

    override fun createCopy() = copy(props = props.copy())

    companion object {

        @JvmStatic
        fun newBuilder() = VerticalScrollBarBuilder()
    }
}
