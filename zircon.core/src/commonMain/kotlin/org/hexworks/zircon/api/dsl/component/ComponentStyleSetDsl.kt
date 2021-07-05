package org.hexworks.zircon.api.dsl.component

import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.component.ComponentStyleSet

/**
 * Creates a new [ComponentStyleSet] using the component builder DSL and returns it.
 */
fun componentStyleSet(init: ComponentStyleSetBuilder.() -> Unit): ComponentStyleSet =
    ComponentStyleSetBuilder().apply(init).build()
