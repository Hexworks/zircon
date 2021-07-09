package org.hexworks.zircon.api.dsl.component

import org.hexworks.zircon.api.builder.component.HBoxBuilder
import org.hexworks.zircon.api.component.Container
import org.hexworks.zircon.api.component.HBox
import org.hexworks.zircon.api.component.builder.base.BaseContainerBuilder

/**
 * Creates a new [HBox] using the component builder DSL and returns it.
 */
fun buildHbox(init: HBoxBuilder.() -> Unit): HBox =
    HBoxBuilder().apply(init).build()

/**
 * Creates a new [HBox] using the component builder DSL, adds it to the
 * receiver [BaseContainerBuilder] it and returns the [HBox].
 */
fun <T : BaseContainerBuilder<*, *>> T.hbox(
    init: HBoxBuilder.() -> Unit
): HBox = buildChildFor(this, HBoxBuilder(), init)