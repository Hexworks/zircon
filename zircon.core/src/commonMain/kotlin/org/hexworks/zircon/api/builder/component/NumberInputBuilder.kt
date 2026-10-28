package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.NumberInput
import org.hexworks.zircon.api.component.builder.base.BaseComponentBuilder
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Size

public abstract class NumberInputBuilder<T : NumberInput>(
    initialRenderer: ComponentRenderer<out T>
) : BaseComponentBuilder<T>(initialRenderer) {

    public var initialValue: Int = 0
        set(value) {
            field = when {
                value < minValue -> minValue
                value > maxValue -> maxValue
                else -> value
            }
        }

    public var minValue: Int = 0
        set(value) {
            require(value <= maxValue) {
                "Can't have a number input with min value ($value) > max value ($maxValue)"
            }
            field = value
            if (initialValue < minValue) {
                initialValue = minValue
            }
        }

    public var maxValue: Int = Int.MAX_VALUE
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

}
