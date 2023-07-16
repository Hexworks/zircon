package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.Slider
import org.hexworks.zircon.api.component.builder.base.BaseContainerBuilder
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.dsl.buildChildFor
import org.hexworks.zircon.internal.component.impl.DefaultVerticalSlider
import org.hexworks.zircon.internal.component.renderer.VerticalSliderRenderer
import org.hexworks.zircon.internal.dsl.ZirconDsl

/**
 * Builder for a vertical [Slider]. By default, it creates a slider with
 * - [minValue]: `0`
 * - [maxValue]: `100`
 * - [numberOfSteps]: `10`
 */
@ZirconDsl
class VerticalSliderBuilder :
    SliderBuilder<Slider>(VerticalSliderRenderer()) {

    override var numberOfSteps: Int = 10
        set(value) {
            require(value in 1..maxValue) { "Number of steps must be greater than 0 and smaller than the maxValue" }
            preferredContentSize = Size.create(
                width = 1,
                height = value + 1
            )
            field = value
        }

    override fun build(): Slider = DefaultVerticalSlider(
        componentMetadata = createMetadata(),
        renderingStrategy = createRenderingStrategy(),
        minValue = minValue,
        maxValue = maxValue,
        numberOfSteps = numberOfSteps,
    ).attachListeners()
}

/**
 * Creates a new [Slider] using the component builder DSL and returns it.
 */
fun buildVerticalSlider(init: VerticalSliderBuilder.() -> Unit): Slider =
    VerticalSliderBuilder().apply(init).build()

/**
 * Creates a new [Slider] using the component builder DSL, adds it to the
 * receiver [BaseContainerBuilder] it and returns the [Slider].
 */
fun <T : BaseContainerBuilder<*>> T.verticalSlider(
    init: VerticalSliderBuilder.() -> Unit
): Slider = buildChildFor(this, VerticalSliderBuilder(), init)
