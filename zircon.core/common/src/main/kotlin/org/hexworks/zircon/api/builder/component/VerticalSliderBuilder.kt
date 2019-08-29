package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.Slider
import org.hexworks.zircon.api.component.base.BaseComponentBuilder
import org.hexworks.zircon.api.component.data.CommonComponentProperties
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.component.renderer.impl.DefaultComponentRenderingStrategy
import org.hexworks.zircon.internal.component.impl.DefaultVerticalSlider
import org.hexworks.zircon.internal.component.renderer.VerticalSliderRenderer
import kotlin.jvm.JvmStatic
import kotlin.math.max

@Suppress("UNCHECKED_CAST")
/**
 * Builder for the slider. By default, it creates a slider with a maxValue of 100 and 10 steps.
 */
data class VerticalSliderBuilder(
        private var minValue: Int = 0,
        private var maxValue: Int = 100,
        private var numberOfSteps: Int = 10,
        private var additionalHeightNeeded: Int = 5,
        override var props: CommonComponentProperties<Slider> = CommonComponentProperties(
                componentRenderer = VerticalSliderRenderer()))
    : BaseComponentBuilder<Slider, VerticalSliderBuilder>() {

    fun withMaxValue(max: Int) = also {
        require(max > minValue) { "Max value must be greater than min value"}
        this.maxValue = max
    }

    fun withMinValue(min: Int) = also {
        require(min > 0) { "Min value must be greater than 0"}
        require(min < maxValue) {"Min value must be smaller than max value"}
        this.minValue = min
    }

    fun withNumberOfSteps(steps: Int) = also {
        require(steps in 1..maxValue) { "Number of steps must be greater 0 and smaller than the maxValue" }
        this.numberOfSteps = steps
        contentSize = contentSize
                .withHeight(max(steps + 1, contentSize.height))
    }

    override fun build(): Slider = DefaultVerticalSlider(
                componentMetadata = ComponentMetadata(
                        size = size,
                        relativePosition = position,
                        componentStyleSet = componentStyleSet,
                        tileset = tileset),
                minValue = minValue,
                maxValue = maxValue,
                numberOfSteps = numberOfSteps,
                renderingStrategy = DefaultComponentRenderingStrategy(
                        decorationRenderers = decorationRenderers,
                        componentRenderer = props.componentRenderer as ComponentRenderer<Slider>))

    override fun createCopy() = copy(props = props.copy())

    companion object {

        @JvmStatic
        fun newBuilder() = VerticalSliderBuilder()
    }
}
