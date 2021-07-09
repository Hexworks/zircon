package org.hexworks.zircon.api.dsl.component

import org.hexworks.zircon.api.builder.component.ButtonBuilder
import org.hexworks.zircon.api.component.Button
import org.hexworks.zircon.api.component.Container
import org.hexworks.zircon.api.component.builder.base.BaseContainerBuilder

/**
 * Creates a new [Button] using the component builder DSL and returns it.
 */
fun buildButton(init: ButtonBuilder.() -> Unit): Button =
    ButtonBuilder().apply(init).build()

/**
 * Creates a new [Button] using the component builder DSL, adds it to the
 * receiver [BaseContainerBuilder] it and returns the [Button].
 */
fun <T : BaseContainerBuilder<*, *>> T.button(
    init: ButtonBuilder.() -> Unit
): Button = buildChildFor(this, ButtonBuilder(), init)