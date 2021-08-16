package org.hexworks.zircon.api.dsl.component

import org.hexworks.zircon.api.builder.component.TextAreaBuilder
import org.hexworks.zircon.api.component.Container
import org.hexworks.zircon.api.component.TextArea
import org.hexworks.zircon.api.component.builder.base.BaseContainerBuilder

/**
 * Creates a new [TextArea] using the component builder DSL and returns it.
 */
fun buildTextArea(init: TextAreaBuilder.() -> Unit): TextArea =
    TextAreaBuilder.newBuilder().apply(init).build()

/**
 * Creates a new [TextArea] using the component builder DSL, adds it to the
 * receiver [BaseContainerBuilder] it and returns the [TextArea].
 */
fun <T : BaseContainerBuilder<*, *>> T.textArea(
    init: TextAreaBuilder.() -> Unit
): TextArea = buildChildFor(this, TextAreaBuilder.newBuilder(), init)

