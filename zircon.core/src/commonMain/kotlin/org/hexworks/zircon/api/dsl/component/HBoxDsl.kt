package org.hexworks.zircon.api.dsl.component

import org.hexworks.zircon.api.builder.component.HBoxBuilder
import org.hexworks.zircon.api.component.HBox

fun hBox(init: HBoxBuilder.() -> Unit): HBox =
    HBoxBuilder().apply(init).build()