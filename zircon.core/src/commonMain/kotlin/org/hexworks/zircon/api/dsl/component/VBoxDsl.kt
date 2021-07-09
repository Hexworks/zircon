package org.hexworks.zircon.api.dsl.component

import org.hexworks.zircon.api.builder.component.VBoxBuilder
import org.hexworks.zircon.api.component.VBox
import org.hexworks.zircon.api.component.builder.base.BaseContainerBuilder

/**
 * Creates a new [VBox] using the component builder DSL and returns it.
 */
fun buildVbox(init: VBoxBuilder.() -> Unit): VBox =
    VBoxBuilder().apply(init).build()

/**
 * Creates a new [VBox] using the component builder DSL, adds it to the
 * receiver [BaseContainerBuilder] it and returns the [VBox].
 */
fun <T : BaseContainerBuilder<*, *>> T.vbox(
    init: VBoxBuilder.() -> Unit
): VBox = buildChildFor(this, VBoxBuilder(), init)