package org.hexworks.zircon.api.dsl.component

import org.hexworks.zircon.api.builder.component.HorizontalNumberInputBuilder
import org.hexworks.zircon.api.component.NumberInput

fun horizontalNumberInput(
    width: Int,
    init: HorizontalNumberInputBuilder.() -> Unit
): NumberInput =
    HorizontalNumberInputBuilder(width).apply(init).build()
