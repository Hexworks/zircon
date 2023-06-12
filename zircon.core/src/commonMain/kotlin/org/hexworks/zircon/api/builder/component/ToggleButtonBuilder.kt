package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.ToggleButton
import org.hexworks.zircon.api.component.builder.base.BaseContainerBuilder
import org.hexworks.zircon.api.component.builder.base.ComponentWithTextBuilder
import org.hexworks.zircon.api.dsl.buildChildFor
import org.hexworks.zircon.internal.component.impl.DefaultToggleButton
import org.hexworks.zircon.internal.component.renderer.DefaultToggleButtonRenderer
import org.hexworks.zircon.internal.dsl.ZirconDsl

@ZirconDsl
class ToggleButtonBuilder : ComponentWithTextBuilder<ToggleButton>(
    initialRenderer = DefaultToggleButtonRenderer(),
    initialText = "",
    reservedSpace = DefaultToggleButtonRenderer.DECORATION_WIDTH
) {

    // TODO: pass property?
    var isSelected: Boolean = false

    override fun build(): ToggleButton {
        return DefaultToggleButton(
            componentMetadata = createMetadata(),
            renderingStrategy = createRenderingStrategy(),
            textProperty = fixedTextProperty,
            initialSelected = isSelected,
        ).attachListeners()
    }
}

/**
 * Creates a new [ToggleButton] using the component builder DSL and returns it.
 */
fun buildToggleButton(init: ToggleButtonBuilder.() -> Unit): ToggleButton =
    ToggleButtonBuilder().apply(init).build()

/**
 * Creates a new [ToggleButton] using the component builder DSL, adds it to the
 * receiver [BaseContainerBuilder] it and returns the [ToggleButton].
 */
fun <T : BaseContainerBuilder<*>> T.toggleButton(
    init: ToggleButtonBuilder.() -> Unit
): ToggleButton = buildChildFor(this, ToggleButtonBuilder(), init)
