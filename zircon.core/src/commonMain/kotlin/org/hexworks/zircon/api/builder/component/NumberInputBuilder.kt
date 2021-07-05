package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.NumberInput
import org.hexworks.zircon.api.component.builder.base.BaseComponentBuilder
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Size

abstract class NumberInputBuilder<T : NumberInput, B : BaseComponentBuilder<T, B>>(
        initialRenderer: ComponentRenderer<out T>
) : BaseComponentBuilder<T, B>(initialRenderer) {

    var initialValue: Int = 0
        set(value) {
            field = when {
                value < minValue -> minValue
                value > maxValue -> maxValue
                else -> value
            }
        }

    var minValue: Int = 0
        set(value) {
            require(value <= maxValue) {
                "Can't have a number input with min value ($value) > max value ($maxValue)"
            }
            field = value
            if (initialValue < minValue) {
                initialValue = minValue
            }
        }

    var maxValue: Int = Int.MAX_VALUE
        set(value) {
            require(value >= minValue) {
                "Can't have a number input with max value ($value) < min value ($minValue)"
            }
            field = value
            if (initialValue > maxValue) {
                initialValue = maxValue
            }
            preferredContentSize = calculateContentSize()
        }

    protected abstract fun calculateContentSize(): Size

    fun withInitialValue(value: Int) = also {
        this.initialValue = value
    }

    fun withMinValue(value: Int) = also {
        this.minValue = value
    }

    fun withMaxValue(value: Int) = also {
        this.maxValue = value
    }

}
