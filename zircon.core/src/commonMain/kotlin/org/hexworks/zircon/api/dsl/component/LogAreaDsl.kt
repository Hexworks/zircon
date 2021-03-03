package org.hexworks.zircon.api.dsl.component

import org.hexworks.zircon.api.builder.component.LogAreaBuilder
import org.hexworks.zircon.api.component.LogArea

fun logArea(init: LogAreaBuilder.() -> Unit): LogArea =
    LogAreaBuilder().apply(init).build()