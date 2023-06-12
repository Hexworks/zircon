package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.ComponentDecorations.side
import org.hexworks.zircon.api.component.Button
import org.hexworks.zircon.api.component.builder.base.BaseContainerBuilder
import org.hexworks.zircon.api.component.builder.base.ComponentWithTextBuilder
import org.hexworks.zircon.api.dsl.buildChildFor
import org.hexworks.zircon.internal.component.impl.DefaultButton
import org.hexworks.zircon.internal.component.renderer.DefaultButtonRenderer
import org.hexworks.zircon.internal.dsl.ZirconDsl

@ZirconDsl
class ButtonBuilder : ComponentWithTextBuilder<Button>(
    initialRenderer = DefaultButtonRenderer(),
    initialText = ""
) {

    init {
        decorationRenderers = listOf(side())
    }

    override fun build(): Button {
        return DefaultButton(
            componentMetadata = createMetadata(),
            renderingStrategy = createRenderingStrategy(),
            textProperty = fixedTextProperty,
        ).attachListeners()
    }
}

/**
 * Creates a new [Button] using the component builder DSL and returns it.
 */
fun buildButton(init: ButtonBuilder.() -> Unit): Button =
    ButtonBuilder().apply(init).build()

/**
 * Creates a new [Button] using the component builder DSL, adds it to the
 * receiver [BaseContainerBuilder] it and returns the [Button].
 */
fun <T : BaseContainerBuilder<*>> T.button(
    init: ButtonBuilder.() -> Unit
): Button = buildChildFor(this, ButtonBuilder(), init)
