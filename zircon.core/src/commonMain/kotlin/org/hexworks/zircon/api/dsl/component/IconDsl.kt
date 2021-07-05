package org.hexworks.zircon.api.dsl.component

import org.hexworks.zircon.api.builder.component.IconBuilder
import org.hexworks.zircon.api.component.Container
import org.hexworks.zircon.api.component.Icon
import org.hexworks.zircon.api.component.builder.base.BaseContainerBuilder

/**
 * Creates a new [Icon] using the component builder DSL and returns it.
 */
fun buildIcon(init: IconBuilder.() -> Unit): Icon =
        IconBuilder().apply(init).build()

/**
 * Creates a new [Icon] using the component builder DSL, adds it to the
 * receiver [BaseContainerBuilder] it and returns the [Icon].
 */
fun <T : BaseContainerBuilder<*, *>> T.icon(
        init: IconBuilder.() -> Unit
): Icon = buildChildFor(this, IconBuilder(), init)
