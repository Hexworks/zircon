package org.hexworks.zircon.api.dsl.component

import org.hexworks.zircon.api.builder.component.PanelBuilder
import org.hexworks.zircon.api.component.Container
import org.hexworks.zircon.api.component.Panel
import org.hexworks.zircon.api.component.builder.base.BaseContainerBuilder

/**
 * Creates a new [Panel] using the component builder DSL and returns it.
 */
fun buildPanel(init: PanelBuilder.() -> Unit): Panel = PanelBuilder.newBuilder()
    .apply(init)
    .build()

/**
 * Creates a new [Panel] using the component builder DSL, adds it to the
 * receiver [BaseContainerBuilder] it and returns the [Panel].
 */
fun <T : BaseContainerBuilder<*, *>> T.panel(
    init: PanelBuilder.() -> Unit
): Panel = buildChildFor(this, PanelBuilder.newBuilder(), init)
