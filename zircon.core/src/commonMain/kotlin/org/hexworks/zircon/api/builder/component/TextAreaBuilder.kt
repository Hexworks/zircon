package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.TextArea
import org.hexworks.zircon.api.component.builder.base.BaseComponentBuilder
import org.hexworks.zircon.api.component.builder.base.BaseContainerBuilder
import org.hexworks.zircon.api.dsl.buildChildFor
import org.hexworks.zircon.internal.component.impl.DefaultTextArea
import org.hexworks.zircon.internal.component.renderer.DefaultTextAreaRenderer
import org.hexworks.zircon.internal.dsl.ZirconDsl

@ZirconDsl
class TextAreaBuilder : BaseComponentBuilder<TextArea>(
    initialRenderer = DefaultTextAreaRenderer(),
) {

    var text: String = ""

    override fun build(): TextArea {
        return DefaultTextArea(
            componentMetadata = createMetadata(),
            renderingStrategy = createRenderingStrategy(),
            initialText = text,
        ).attachListeners()
    }
}

/**
 * Creates a new [TextArea] using the component builder DSL and returns it.
 */
fun buildTextArea(init: TextAreaBuilder.() -> Unit): TextArea =
    TextAreaBuilder().apply(init).build()

/**
 * Creates a new [TextArea] using the component builder DSL, adds it to the
 * receiver [BaseContainerBuilder] it and returns the [TextArea].
 */
fun <T : BaseContainerBuilder<*>> T.textArea(
    init: TextAreaBuilder.() -> Unit
): TextArea = buildChildFor(this, TextAreaBuilder(), init)
