package org.hexworks.zircon.api.dsl.component

import org.hexworks.zircon.api.builder.component.VerticalNumberInputBuilder
import org.hexworks.zircon.api.component.NumberInput

fun verticalNumberInput(height: Int, init: VerticalNumberInputBuilder.() -> Unit): NumberInput =
    VerticalNumberInputBuilder(height).apply(init).build()