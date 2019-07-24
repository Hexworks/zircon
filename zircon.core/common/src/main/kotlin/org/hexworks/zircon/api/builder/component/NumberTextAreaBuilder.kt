package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.NumberTextArea
import org.hexworks.zircon.api.component.TextArea
import org.hexworks.zircon.api.component.base.BaseComponentBuilder
import org.hexworks.zircon.api.component.data.CommonComponentProperties
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.component.renderer.impl.DefaultComponentRenderingStrategy
import org.hexworks.zircon.internal.component.impl.DefaultNumberTextArea
import org.hexworks.zircon.internal.component.renderer.DefaultNumberTextAreaRenderer
import kotlin.jvm.JvmStatic
import kotlin.math.max

@Suppress("UNCHECKED_CAST")
data class NumberTextAreaBuilder(
        private var initialValue: Int = 0,
        private var maxValue: Int = Int.MAX_VALUE,
        override val props: CommonComponentProperties<TextArea> = CommonComponentProperties(
                componentRenderer = DefaultNumberTextAreaRenderer()))
    : BaseComponentBuilder<TextArea, NumberTextAreaBuilder>() {

    fun withInitialValue(value: Int) = also {
        this.initialValue = value
        contentSize = contentSize
                .withWidth(max(this.initialValue.toString().length, contentSize.width))
    }

    fun withMaxValue(value: Int) = also {
        this.maxValue = value
    }

    override fun build(): NumberTextArea {
        return DefaultNumberTextArea(
                componentMetadata = ComponentMetadata(
                        size = size,
                        position = position,
                        componentStyleSet = componentStyleSet,
                        tileset = tileset),
                initialValue = initialValue,
                maxValue = maxValue,
                renderingStrategy = DefaultComponentRenderingStrategy(
                        decorationRenderers = decorationRenderers,
                        componentRenderer = props.componentRenderer as ComponentRenderer<TextArea>))
    }

    override fun createCopy() = copy()

    companion object {

        @JvmStatic
        fun newBuilder() = NumberTextAreaBuilder()
    }
}
