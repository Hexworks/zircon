package org.hexworks.zircon.api.dsl.component

import org.hexworks.zircon.api.builder.component.ToggleButtonBuilder
import org.hexworks.zircon.api.component.ToggleButton

fun toggleButton(init: ToggleButtonBuilder.() -> Unit): ToggleButton =
    ToggleButtonBuilder().apply(init).build()