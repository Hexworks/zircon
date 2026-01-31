package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.Label
import org.hexworks.zircon.api.component.builder.base.BaseContainerBuilder
import org.hexworks.zircon.api.component.builder.base.ComponentWithTextBuilder
import org.hexworks.zircon.api.dsl.buildChildFor
import org.hexworks.zircon.internal.component.impl.DefaultLabel
import org.hexworks.zircon.internal.component.renderer.DefaultLabelRenderer
import org.hexworks.zircon.internal.dsl.ZirconDsl

@ZirconDsl
class LabelBuilder : ComponentWithTextBuilder<Label>(
    initialRenderer = DefaultLabelRenderer(),
    initialText = ""
) {

    override fun build(): Label {
        return DefaultLabel(
            componentMetadata = createMetadata(),
            renderingStrategy = createRenderingStrategy(),
            textProperty = fixedTextProperty,
        ).attachListeners()
    }
}

/**
 * Creates a new [Label] using the component builder DSL and returns it.
 */
fun buildLabel(init: LabelBuilder.() -> Unit): Label =
    LabelBuilder().apply(init).build()

/**
 * Creates a new [Label] using the component builder DSL, adds it to the
 * receiver [BaseContainerBuilder] it and returns the [Label].
 */
fun <T : BaseContainerBuilder<*>> T.label(
    init: LabelBuilder.() -> Unit
): Label = buildChildFor(this, LabelBuilder(), init)
