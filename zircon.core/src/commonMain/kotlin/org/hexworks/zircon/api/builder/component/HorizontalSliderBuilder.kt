package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.Slider
import org.hexworks.zircon.api.component.builder.base.BaseContainerBuilder
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.dsl.buildChildFor
import org.hexworks.zircon.internal.component.impl.DefaultHorizontalSlider
import org.hexworks.zircon.internal.component.renderer.HorizontalSliderRenderer
import org.hexworks.zircon.internal.dsl.ZirconDsl

/**
 * Builder for a horizontal [Slider]. By default, it creates a slider with
 * - [minValue]: `0`
 * - [maxValue]: `100`
 * - [numberOfSteps]: `10`
 */
@ZirconDsl
class HorizontalSliderBuilder : SliderBuilder<Slider>(
    initialRenderer = HorizontalSliderRenderer()
) {

    override var numberOfSteps: Int = 10
        set(value) {
            require(value in 1..maxValue) { "Number of steps must be greater than 0 and smaller than the maxValue" }
            preferredContentSize = Size.create(
                width = value + 1,
                height = 1
            )
            field = value
        }

    override fun build(): Slider = DefaultHorizontalSlider(
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
fun buildHorizontalSlider(init: HorizontalSliderBuilder.() -> Unit): Slider =
    HorizontalSliderBuilder().apply(init).build()

/**
 * Creates a new [Slider] using the component builder DSL, adds it to the
 * receiver [BaseContainerBuilder] it and returns the [Slider].
 */
fun <T : BaseContainerBuilder<*>> T.horizontalSlider(
    init: HorizontalSliderBuilder.() -> Unit
): Slider = buildChildFor(this, HorizontalSliderBuilder(), init)
