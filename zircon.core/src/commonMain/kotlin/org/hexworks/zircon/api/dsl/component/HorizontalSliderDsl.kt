package org.hexworks.zircon.api.dsl.component

import org.hexworks.zircon.api.builder.component.HorizontalSliderBuilder
import org.hexworks.zircon.api.component.Slider

fun horizontalSlider(init: HorizontalSliderBuilder.() -> Unit): Slider =
    HorizontalSliderBuilder().apply(init).build()