package org.hexworks.zircon.api.dsl.component

import org.hexworks.zircon.api.builder.component.LabelBuilder
import org.hexworks.zircon.api.component.Label

fun label(init: LabelBuilder.() -> Unit): Label =
    LabelBuilder().apply(init).build()