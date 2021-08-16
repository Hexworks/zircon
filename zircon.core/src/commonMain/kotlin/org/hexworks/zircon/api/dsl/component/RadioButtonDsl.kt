package org.hexworks.zircon.api.dsl.component

import org.hexworks.zircon.api.builder.component.RadioButtonBuilder
import org.hexworks.zircon.api.component.Container
import org.hexworks.zircon.api.component.RadioButton
import org.hexworks.zircon.api.component.builder.base.BaseContainerBuilder

/**
 * Creates a new [RadioButton] using the component builder DSL and returns it.
 */
fun buildRadioButton(init: RadioButtonBuilder.() -> Unit): RadioButton =
    RadioButtonBuilder.newBuilder().apply(init).build()

/**
 * Creates a new [RadioButton] using the component builder DSL, adds it to the
 * receiver [BaseContainerBuilder] it and returns the [RadioButton].
 */
fun <T : BaseContainerBuilder<*, *>> T.radioButton(
    init: RadioButtonBuilder.() -> Unit
): RadioButton = buildChildFor(this, RadioButtonBuilder.newBuilder(), init)
