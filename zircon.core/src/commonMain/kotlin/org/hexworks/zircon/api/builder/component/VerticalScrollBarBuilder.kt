package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.behavior.Scrollable
import org.hexworks.zircon.api.builder.data.size
import org.hexworks.zircon.api.component.ScrollBar
import org.hexworks.zircon.api.component.builder.base.BaseComponentBuilder
import org.hexworks.zircon.api.component.builder.base.BaseContainerBuilder
import org.hexworks.zircon.api.dsl.buildChildFor
import org.hexworks.zircon.internal.component.impl.DefaultVerticalScrollBar
import org.hexworks.zircon.internal.component.renderer.DefaultVerticalScrollBarRenderer
import org.hexworks.zircon.internal.dsl.ZirconDsl

@ZirconDsl
class VerticalScrollBarBuilder(
    initialScrollable: Scrollable
) : BaseComponentBuilder<ScrollBar>(
    initialRenderer = DefaultVerticalScrollBarRenderer()
) {

    var scrollable: Scrollable = initialScrollable

    override fun build(): ScrollBar {
        val visibleSize = scrollable.visibleSize
        preferredSize = size {
            width = 1
            height = visibleSize.height
        }
        return DefaultVerticalScrollBar(
            componentMetadata = createMetadata(),
            renderingStrategy = createRenderingStrategy(),
            scrollable = scrollable
        ).attachListeners()
    }
}

/**
 * Creates a new vertical [ScrollBar] using the component builder DSL and returns it.
 */
fun buildVerticalScrollBar(scrollable: Scrollable, init: VerticalScrollBarBuilder.() -> Unit): ScrollBar =
    VerticalScrollBarBuilder(scrollable).apply(init).build()

/**
 * Creates a new vertical [ScrollBar] using the component builder DSL, adds it to the
 * receiver [BaseContainerBuilder] it and returns the [ScrollBar].
 */
fun <T : BaseContainerBuilder<*>> T.verticalScrollBar(
    scrollable: Scrollable,
    init: VerticalScrollBarBuilder.() -> Unit
): ScrollBar = buildChildFor(this, VerticalScrollBarBuilder(scrollable), init)
