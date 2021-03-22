package org.hexworks.zircon.api.dsl.component

import org.hexworks.zircon.api.builder.component.RadioButtonBuilder
import org.hexworks.zircon.api.component.RadioButton

fun radioButton(init: RadioButtonBuilder.() -> Unit): RadioButton =
    RadioButtonBuilder().apply(init).build()