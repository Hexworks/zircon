package org.hexworks.zircon.api.dsl.component

import org.hexworks.zircon.api.builder.component.HeaderBuilder
import org.hexworks.zircon.api.component.Container
import org.hexworks.zircon.api.component.Header
import org.hexworks.zircon.api.component.builder.base.BaseContainerBuilder

/**
 * Creates a new [Header] using the component builder DSL and returns it.
 */
fun buildHeader(init: HeaderBuilder.() -> Unit): Header =
    HeaderBuilder().apply(init).build()

/**
 * Creates a new [Header] using the component builder DSL, adds it to the
 * receiver [BaseContainerBuilder] it and returns the [Header].
 */
fun <T : BaseContainerBuilder<*, *>> T.header(
    init: HeaderBuilder.() -> Unit
): Header = buildChildFor(this, HeaderBuilder(), init)