package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.NumberInput
import org.hexworks.zircon.api.component.TextArea
import org.hexworks.zircon.api.component.base.BaseComponentBuilder
import org.hexworks.zircon.api.component.data.CommonComponentProperties
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.component.renderer.impl.DefaultComponentRenderingStrategy
import org.hexworks.zircon.internal.component.impl.DefaultNumberInput
import org.hexworks.zircon.internal.component.renderer.DefaultNumberInputRenderer
import kotlin.jvm.JvmStatic
import kotlin.math.max

@Suppress("UNCHECKED_CAST")
data class NumberInputBuilder(
        val width: Int,
        private var initialValue: Int = 0,
        private var maxValue: Int = Int.MAX_VALUE,
        override val props: CommonComponentProperties<NumberInput> = CommonComponentProperties(
                componentRenderer = DefaultNumberInputRenderer()))
    : BaseComponentBuilder<NumberInput, NumberInputBuilder>() {

    fun withInitialValue(value: Int) = also {
        this.initialValue = value
        contentSize = contentSize
                .withWidth(max(this.initialValue.toString().length, width))
                .withHeight(1)
    }

    fun withMaxValue(value: Int) = also {
        this.maxValue = value
    }

    override fun build(): NumberInput {
        return DefaultNumberInput(
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
        fun newBuilder(width: Int) = NumberInputBuilder(width = width)
    }
}
