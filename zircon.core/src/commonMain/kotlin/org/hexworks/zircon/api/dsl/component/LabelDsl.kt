package org.hexworks.zircon.api.dsl.component

import org.hexworks.zircon.api.builder.component.LabelBuilder
import org.hexworks.zircon.api.component.Container
import org.hexworks.zircon.api.component.Label
import org.hexworks.zircon.api.component.builder.base.BaseContainerBuilder

/**
 * Creates a new [Label] using the component builder DSL and returns it.
 */
fun buildLabel(init: LabelBuilder.() -> Unit): Label =
    LabelBuilder.newBuilder().apply(init).build()

/**
 * Creates a new [Label] using the component builder DSL, adds it to the
 * receiver [BaseContainerBuilder] it and returns the [Label].
 */
fun <T : BaseContainerBuilder<*, *>> T.label(
    init: LabelBuilder.() -> Unit
): Label = buildChildFor(this, LabelBuilder.newBuilder(), init)
