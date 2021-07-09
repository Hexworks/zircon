package org.hexworks.zircon.api.dsl.component

import org.hexworks.zircon.api.builder.component.RadioButtonGroupBuilder
import org.hexworks.zircon.api.component.RadioButtonGroup

/**
 * Creates a new [RadioButtonGroup] using the component builder DSL and returns it.
 */
fun radioButtonGroup(init: RadioButtonGroupBuilder.() -> Unit): RadioButtonGroup =
    RadioButtonGroupBuilder().apply(init).build()
