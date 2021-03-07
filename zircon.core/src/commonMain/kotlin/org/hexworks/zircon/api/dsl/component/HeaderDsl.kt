package org.hexworks.zircon.api.dsl.component

import org.hexworks.zircon.api.builder.component.HeaderBuilder
import org.hexworks.zircon.api.component.Header

fun header(init: HeaderBuilder.() -> Unit): Header =
    HeaderBuilder().apply(init).build()