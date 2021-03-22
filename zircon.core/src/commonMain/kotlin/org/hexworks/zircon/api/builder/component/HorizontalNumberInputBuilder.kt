package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.NumberInput
import org.hexworks.zircon.api.component.builder.base.BaseComponentBuilder
import org.hexworks.zircon.internal.dsl.ZirconDsl
import org.hexworks.zircon.internal.component.impl.DefaultHorizontalNumberInput
import org.hexworks.zircon.internal.component.renderer.DefaultNumberInputRenderer
import kotlin.jvm.JvmStatic
import kotlin.math.max

@Suppress("UNCHECKED_CAST")
@ZirconDsl
class HorizontalNumberInputBuilder(
    val width: Int,
    private var initialValue: Int = 0,
    private var minValue: Int = 0,
    private var maxValue: Int = Int.MAX_VALUE
) : BaseComponentBuilder<NumberInput, HorizontalNumberInputBuilder>(DefaultNumberInputRenderer()) {

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
            .withWidth(max(this.maxValue.toString().length + 1, width))
            .withHeight(1)
    }

    fun withMinValue(value: Int) = also {
        minValue = value
        if (initialValue < minValue) {
            initialValue = minValue
        }
    }

    override fun build(): NumberInput = DefaultHorizontalNumberInput(
        componentMetadata = createMetadata(),
        renderingStrategy = createRenderingStrategy(),
        initialValue = initialValue,
        minValue = minValue,
        maxValue = maxValue,
    )

    override fun createCopy() = newBuilder(width).withProps(props.copy())
        .withInitialValue(initialValue)
        .withMinValue(minValue)
        .withMaxValue(maxValue)

    companion object {

        @JvmStatic
        fun newBuilder(width: Int) = HorizontalNumberInputBuilder(width = width)
    }
}
