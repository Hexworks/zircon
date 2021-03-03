package org.hexworks.zircon.api.dsl.component

import org.hexworks.zircon.api.builder.component.VerticalScrollBarBuilder
import org.hexworks.zircon.api.component.ScrollBar

fun verticalScrollBar(
    minValue: Int = 0,
    maxValue: Int = 100,
    init: VerticalScrollBarBuilder.() -> Unit
): ScrollBar =
    VerticalScrollBarBuilder(minValue, maxValue).apply(init).build()