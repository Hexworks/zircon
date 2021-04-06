package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.Slider
import org.hexworks.zircon.api.component.builder.base.BaseComponentBuilder
import org.hexworks.zircon.internal.dsl.ZirconDsl
import org.hexworks.zircon.internal.component.impl.DefaultVerticalSlider
import org.hexworks.zircon.internal.component.renderer.VerticalSliderRenderer
import kotlin.jvm.JvmStatic
import kotlin.math.max

@Suppress("UNCHECKED_CAST")
/**
 * Builder for a vertical [Slider]. By default, it creates a slider with
 * - [minValue]: `0`
 * - [maxValue]: `100`
 * - [numberOfSteps]: `10`
 */
@ZirconDsl
class VerticalSliderBuilder(
    private var minValue: Int = 0,
    private var maxValue: Int = 100,
    private var numberOfSteps: Int = 10,
) : BaseComponentBuilder<Slider, VerticalSliderBuilder>(VerticalSliderRenderer()) {

    fun withMaxValue(max: Int) = also {
        require(max > minValue) { "Max value must be greater than min value" }
        this.maxValue = max
    }

    fun withMinValue(min: Int) = also {
        require(min >= 0) { "Min value must be equal to or greater than 0" }
        require(min < maxValue) { "Min value must be smaller than max value" }
        this.minValue = min
    }

    fun withNumberOfSteps(steps: Int) = also {
        require(steps in 1..maxValue) { "Number of steps must be greater than 0 and smaller than the maxValue" }
        this.numberOfSteps = steps
        contentSize = contentSize
            .withHeight(max(steps + 1, contentSize.height))
    }

    override fun build(): Slider = DefaultVerticalSlider(
        componentMetadata = createMetadata(),
        renderingStrategy = createRenderingStrategy(),
        minValue = minValue,
        maxValue = maxValue,
        numberOfSteps = numberOfSteps,
    )

    override fun createCopy() = newBuilder().withProps(props.copy())
        .withMinValue(minValue)
        .withMaxValue(maxValue)
        .withNumberOfSteps(numberOfSteps)

    companion object {

        @JvmStatic
        fun newBuilder() = VerticalSliderBuilder()
    }
}
