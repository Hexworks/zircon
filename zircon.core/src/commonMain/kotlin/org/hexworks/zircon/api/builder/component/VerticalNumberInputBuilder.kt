package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.NumberInput
import org.hexworks.zircon.api.component.builder.base.BaseComponentBuilder
import org.hexworks.zircon.api.component.data.CommonComponentProperties
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.internal.component.impl.DefaultVerticalNumberInput
import org.hexworks.zircon.internal.component.renderer.DefaultComponentRenderingStrategy
import org.hexworks.zircon.internal.component.renderer.DefaultVerticalNumberInputRenderer
import kotlin.jvm.JvmStatic
import kotlin.math.max

@Suppress("UNCHECKED_CAST")
class VerticalNumberInputBuilder(
        val height: Int,
        private var initialValue: Int = 0,
        private var minValue: Int = 0,
        private var maxValue: Int = Int.MAX_VALUE)
    : BaseComponentBuilder<NumberInput, VerticalNumberInputBuilder>(
        DefaultVerticalNumberInputRenderer() as ComponentRenderer<NumberInput>) {

    fun withInitialValue(value: Int) = also {
        initialValue = when {
            value < minValue -> minValue
            value > maxValue -> maxValue
            else -> value
        }
    }

    fun withMaxValue(value: Int) = also {
        maxValue = value
        if (initialValue > maxValue) {
            initialValue = maxValue
        }
        contentSize = contentSize
                .withHeight(max(this.maxValue.toString().length + 1, height))
                .withWidth(1)
    }

    fun withMinValue(value: Int) = also {
        minValue = value
        if (initialValue < minValue) {
            initialValue = minValue
        }
    }

    override fun build(): NumberInput = DefaultVerticalNumberInput(
            componentMetadata = ComponentMetadata(
                    size = size,
                    relativePosition = position,
                    componentStyleSet = componentStyleSet,
                    tileset = tileset),
            initialValue = initialValue,
            minValue = minValue,
            maxValue = maxValue,
            renderingStrategy = DefaultComponentRenderingStrategy(
                    decorationRenderers = decorationRenderers,
                    componentRenderer = props.componentRenderer as ComponentRenderer<NumberInput>)).apply {
        colorTheme.map {
            theme = it
        }
    }

    override fun createCopy() = newBuilder(height)
            .withProps(props.copy())
            .withInitialValue(initialValue)
            .withMinValue(minValue)
            .withMaxValue(maxValue)

    companion object {

        @JvmStatic
        fun newBuilder(height: Int) = VerticalNumberInputBuilder(height = height)
    }
}
