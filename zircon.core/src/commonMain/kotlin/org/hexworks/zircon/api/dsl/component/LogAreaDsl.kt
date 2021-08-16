package org.hexworks.zircon.api.dsl.component

import org.hexworks.zircon.api.builder.component.LogAreaBuilder
import org.hexworks.zircon.api.component.Container
import org.hexworks.zircon.api.component.LogArea
import org.hexworks.zircon.api.component.builder.base.BaseContainerBuilder

/**
 * Creates a new [LogArea] using the component builder DSL and returns it.
 */
fun buildLogArea(init: LogAreaBuilder.() -> Unit): LogArea =
    LogAreaBuilder.newBuilder().apply(init).build()

/**
 * Creates a new [LogArea] using the component builder DSL, adds it to the
 * receiver [BaseContainerBuilder] it and returns the [LogArea].
 */
fun <T : BaseContainerBuilder<*, *>> T.logArea(
    init: LogAreaBuilder.() -> Unit
): LogArea = buildChildFor(this, LogAreaBuilder.newBuilder(), init)
