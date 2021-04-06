package org.hexworks.zircon.api.dsl.component

import org.hexworks.zircon.api.builder.component.HorizontalScrollBarBuilder
import org.hexworks.zircon.api.component.ScrollBar

fun horizontalScrollBar(
    minValue: Int = 0,
    maxValue: Int = 100,
    init: HorizontalScrollBarBuilder.() -> Unit
): ScrollBar =
    HorizontalScrollBarBuilder(minValue, maxValue).apply(init).build()