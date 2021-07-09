package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.Slider
import org.hexworks.zircon.api.component.builder.base.BaseComponentBuilder
import org.hexworks.zircon.api.component.renderer.ComponentRenderer

abstract class SliderBuilder<T : Slider, B : BaseComponentBuilder<T, B>>(
    initialRenderer: ComponentRenderer<out T>
) : BaseComponentBuilder<T, B>(initialRenderer) {

    var minValue: Int = 0
        set(value) {
            require(value >= 0) { "Min value must be equal to or greater than 0" }
            require(value < maxValue) { "Min value must be smaller than max value" }
            field = value
        }

    var maxValue: Int = 100
        set(value) {
            require(value > minValue) { "Max value must be greater than min value" }
            field = value
        }

    abstract var numberOfSteps: Int

    fun withMinValue(minValue: Int) = also {
        this.minValue = minValue
    }

    fun withMaxValue(maxValue: Int) = also {
        this.maxValue = maxValue
    }

    fun withNumberOfSteps(steps: Int) = also {
        this.numberOfSteps = steps
    }
}
