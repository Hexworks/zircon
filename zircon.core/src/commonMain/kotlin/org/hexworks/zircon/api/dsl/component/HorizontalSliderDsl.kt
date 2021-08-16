package org.hexworks.zircon.api.dsl.component

import org.hexworks.zircon.api.builder.component.HorizontalSliderBuilder
import org.hexworks.zircon.api.component.Container
import org.hexworks.zircon.api.component.Slider
import org.hexworks.zircon.api.component.builder.base.BaseContainerBuilder

/**
 * Creates a new [Slider] using the component builder DSL and returns it.
 */
fun buildHorizontalSlider(init: HorizontalSliderBuilder.() -> Unit): Slider =
    HorizontalSliderBuilder.newBuilder().apply(init).build()

/**
 * Creates a new [Slider] using the component builder DSL, adds it to the
 * receiver [BaseContainerBuilder] it and returns the [Slider].
 */
fun <T : BaseContainerBuilder<*, *>> T.horizontalSlider(
    init: HorizontalSliderBuilder.() -> Unit
): Slider = buildChildFor(this, HorizontalSliderBuilder.newBuilder(), init)
