package org.hexworks.zircon.api.dsl.component

import org.hexworks.zircon.api.builder.component.VBoxBuilder
import org.hexworks.zircon.api.component.VBox

fun vBox(init: VBoxBuilder.() -> Unit): VBox =
    VBoxBuilder().apply(init).build()