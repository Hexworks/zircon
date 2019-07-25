package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.Slider
import org.hexworks.zircon.api.component.base.BaseComponentBuilder
import org.hexworks.zircon.api.component.data.CommonComponentProperties
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.component.renderer.impl.DefaultComponentRenderingStrategy
import org.hexworks.zircon.internal.component.impl.DefaultHorizontalSlider
import org.hexworks.zircon.internal.component.renderer.HorizontalSliderRenderer
import kotlin.jvm.JvmStatic
import kotlin.math.max

@Suppress("UNCHECKED_CAST")
/**
 * Builder for the slider. By default, it creates a slider with a range of 100 and 10 steps.
 */
data class HorizontalSliderBuilder(
        private var range: Int = 100,
        private var numberOfSteps: Int = 10,
        private var additionalWidthNeeded: Int = 5,
        override var props: CommonComponentProperties<Slider> = CommonComponentProperties(
                componentRenderer = HorizontalSliderRenderer()))
    : BaseComponentBuilder<Slider, HorizontalSliderBuilder>() {

    fun withRange(range: Int) = also {
        require(range > 0) { "Range must be greater than 0"}
        this.range = range
    }

    fun withNumberOfSteps(steps: Int) = also {
        this.numberOfSteps = steps
        contentSize = contentSize
                .withWidth(max(steps + 1, contentSize.width))
    }

    override fun build(): Slider = DefaultHorizontalSlider(
                componentMetadata = ComponentMetadata(
                        size = size,
                        position = position,
                        componentStyleSet = componentStyleSet,
                        tileset = tileset),
                range = range,
                numberOfSteps = numberOfSteps,
                renderingStrategy = DefaultComponentRenderingStrategy(
                        decorationRenderers = decorationRenderers,
                        componentRenderer = props.componentRenderer as ComponentRenderer<Slider>))

    override fun createCopy() = copy(props = props.copy())

    companion object {

        @JvmStatic
        fun newBuilder() = HorizontalSliderBuilder()
    }
}