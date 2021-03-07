package org.hexworks.zircon.api.dsl.component

import org.hexworks.zircon.api.builder.component.ButtonBuilder
import org.hexworks.zircon.api.component.Button

fun button(init: ButtonBuilder.() -> Unit): Button =
    ButtonBuilder().apply(init).build()
