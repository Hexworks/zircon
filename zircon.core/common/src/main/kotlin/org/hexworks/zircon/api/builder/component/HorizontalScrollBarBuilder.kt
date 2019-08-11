package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.ScrollBar
import org.hexworks.zircon.api.component.base.BaseComponentBuilder
import org.hexworks.zircon.api.component.data.CommonComponentProperties
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.component.renderer.impl.DefaultComponentRenderingStrategy
import org.hexworks.zircon.internal.component.impl.DefaultHorizontalScrollBar
import org.hexworks.zircon.internal.component.impl.DefaultVerticalScrollBar
import org.hexworks.zircon.internal.component.renderer.HorizontalScrollBarRenderer
import org.hexworks.zircon.internal.component.renderer.VerticalScrollBarRenderer
import kotlin.jvm.JvmStatic

@Suppress("UNCHECKED_CAST")
/**
 * Builder for the scrollbar. By default, it creates a scrollbar with a maxValue of 100.
 */
data class HorizontalScrollBarBuilder(
        private var minValue: Int = 0,
        private var maxValue: Int = 100,
        override var props: CommonComponentProperties<ScrollBar> = CommonComponentProperties(
                componentRenderer = HorizontalScrollBarRenderer()))
    : BaseComponentBuilder<ScrollBar, HorizontalScrollBarBuilder>() {

    fun withNumberOfScrollableItems(max: Int) = also {
        require(max > 0) { "Max value must be greater than min value" }
        this.maxValue = max
    }

    override fun build(): ScrollBar = DefaultHorizontalScrollBar(
            componentMetadata = ComponentMetadata(
                    size = size,
                    position = position,
                    componentStyleSet = componentStyleSet,
                    tileset = tileset),
            minValue = minValue,
            maxValue = maxValue,
            itemsShownAtOnce = size.width,
            numberOfSteps = size.width,
            renderingStrategy = DefaultComponentRenderingStrategy(
                    decorationRenderers = decorationRenderers,
                    componentRenderer = props.componentRenderer as ComponentRenderer<ScrollBar>))

    override fun createCopy() = copy(props = props.copy())

    companion object {

        @JvmStatic
        fun newBuilder() = HorizontalScrollBarBuilder()
    }
}