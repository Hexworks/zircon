package org.hexworks.zircon.api.dsl.component

import org.hexworks.zircon.api.builder.component.VerticalNumberInputBuilder
import org.hexworks.zircon.api.component.NumberInput
import org.hexworks.zircon.api.component.builder.base.BaseContainerBuilder

/**
 * Creates a new [NumberInput] using the component builder DSL and returns it.
 */
fun buildVerticalNumberInput(
        init: VerticalNumberInputBuilder.() -> Unit
): NumberInput = VerticalNumberInputBuilder().apply(init).build()

/**
 * Creates a new [NumberInput] using the component builder DSL, adds it to the
 * receiver [BaseContainerBuilder] it and returns the [NumberInput].
 */
fun <T : BaseContainerBuilder<*, *>> T.verticalNumberInput(
        init: VerticalNumberInputBuilder.() -> Unit
): NumberInput = buildChildFor(this, VerticalNumberInputBuilder(), init)