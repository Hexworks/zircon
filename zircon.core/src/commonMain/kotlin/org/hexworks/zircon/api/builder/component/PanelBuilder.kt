package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.Panel
import org.hexworks.zircon.api.component.builder.base.BaseContainerBuilder
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.dsl.buildChildFor
import org.hexworks.zircon.internal.component.impl.DefaultPanel
import org.hexworks.zircon.internal.component.renderer.DefaultPanelRenderer
import org.hexworks.zircon.internal.dsl.ZirconDsl

@ZirconDsl
class PanelBuilder : BaseContainerBuilder<Panel>(
    initialRenderer = DefaultPanelRenderer()
) {

    override fun build(): Panel {
        return DefaultPanel(
            componentMetadata = createMetadata(),
            renderingStrategy = createRenderingStrategy(),
            initialTitle = title,
        ).apply {
            addComponents(*childrenToAdd.toTypedArray())
        }.attachListeners()
    }

    override fun calculateContentSize(): Size {
        // best effort TODO?
        val result = childrenToAdd.map { it.size }.fold(Size.zero(), Size::plus)
        return if (result < Size.one()) Size.one() else result
    }

}

/**
 * Creates a new [Panel] using the component builder DSL and returns it.
 */
fun buildPanel(init: PanelBuilder.() -> Unit): Panel = PanelBuilder()
    .apply(init)
    .build()

/**
 * Creates a new [Panel] using the component builder DSL, adds it to the
 * receiver [BaseContainerBuilder] it and returns the [Panel].
 */
fun <T : BaseContainerBuilder<*>> T.panel(
    init: PanelBuilder.() -> Unit
): Panel = buildChildFor(this, PanelBuilder(), init)
