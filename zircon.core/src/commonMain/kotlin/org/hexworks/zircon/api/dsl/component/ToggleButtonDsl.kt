package org.hexworks.zircon.api.dsl.component

import org.hexworks.zircon.api.builder.component.ToggleButtonBuilder
import org.hexworks.zircon.api.component.Container
import org.hexworks.zircon.api.component.ToggleButton
import org.hexworks.zircon.api.component.builder.base.BaseContainerBuilder

/**
 * Creates a new [ToggleButton] using the component builder DSL and returns it.
 */
fun buildToggleButton(init: ToggleButtonBuilder.() -> Unit): ToggleButton =
        ToggleButtonBuilder().apply(init).build()

/**
 * Creates a new [ToggleButton] using the component builder DSL, adds it to the
 * receiver [BaseContainerBuilder] it and returns the [ToggleButton].
 */
fun <T : BaseContainerBuilder<*, *>> T.toggleButton(
        init: ToggleButtonBuilder.() -> Unit
): ToggleButton = buildChildFor(this, ToggleButtonBuilder(), init)