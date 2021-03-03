package org.hexworks.zircon.api.dsl.component

import org.hexworks.zircon.api.builder.component.VerticalSliderBuilder
import org.hexworks.zircon.api.component.Slider

fun verticalSlider(init: VerticalSliderBuilder.() -> Unit): Slider =
    VerticalSliderBuilder().apply(init).build()