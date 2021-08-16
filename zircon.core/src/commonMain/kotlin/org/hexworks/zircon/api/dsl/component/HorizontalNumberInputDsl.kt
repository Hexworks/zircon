package org.hexworks.zircon.api.dsl.component

import org.hexworks.zircon.api.builder.component.HorizontalNumberInputBuilder
import org.hexworks.zircon.api.component.Container
import org.hexworks.zircon.api.component.NumberInput
import org.hexworks.zircon.api.component.builder.base.BaseContainerBuilder

/**
 * Creates a new [NumberInput] using the component builder DSL and returns it.
 */
fun buildHorizontalNumberInput(
    init: HorizontalNumberInputBuilder.() -> Unit
): NumberInput =
    HorizontalNumberInputBuilder.newBuilder().apply(init).build()

/**
 * Creates a new [NumberInput] using the component builder DSL, adds it to the
 * receiver [BaseContainerBuilder] it and returns the [NumberInput].
 */
fun <T : BaseContainerBuilder<*, *>> T.horizontalNumberInput(
    init: HorizontalNumberInputBuilder.() -> Unit
): NumberInput = buildChildFor(this, HorizontalNumberInputBuilder.newBuilder(), init)
