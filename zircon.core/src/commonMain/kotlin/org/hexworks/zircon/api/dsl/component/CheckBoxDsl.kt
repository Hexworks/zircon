package org.hexworks.zircon.api.dsl.component

import org.hexworks.zircon.api.builder.component.CheckBoxBuilder
import org.hexworks.zircon.api.component.CheckBox
import org.hexworks.zircon.api.component.Container
import org.hexworks.zircon.api.component.builder.base.BaseContainerBuilder

/**
 * Creates a new [CheckBox] using the component builder DSL and returns it.
 */
fun buildCheckBox(init: CheckBoxBuilder.() -> Unit): CheckBox =
    CheckBoxBuilder().apply(init).build()

/**
 * Creates a new [CheckBox] using the component builder DSL, adds it to the
 * receiver [BaseContainerBuilder] it and returns the [CheckBox].
 */
fun <T : BaseContainerBuilder<*, *>> T.checkBox(
    init: CheckBoxBuilder.() -> Unit
): CheckBox = buildChildFor(this, CheckBoxBuilder(), init)