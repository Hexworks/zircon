package org.hexworks.zircon.api.dsl.component

import org.hexworks.zircon.api.builder.component.VerticalSliderBuilder
import org.hexworks.zircon.api.component.Slider
import org.hexworks.zircon.api.component.builder.base.BaseContainerBuilder

/**
 * Creates a new [Slider] using the component builder DSL and returns it.
 */
fun buildVerticalSlider(init: VerticalSliderBuilder.() -> Unit): Slider =
        VerticalSliderBuilder().apply(init).build()

/**
 * Creates a new [Slider] using the component builder DSL, adds it to the
 * receiver [BaseContainerBuilder] it and returns the [Slider].
 */
fun <T : BaseContainerBuilder<*, *>> T.verticalSlider(
        init: VerticalSliderBuilder.() -> Unit
): Slider = buildChildFor(this, VerticalSliderBuilder(), init)
