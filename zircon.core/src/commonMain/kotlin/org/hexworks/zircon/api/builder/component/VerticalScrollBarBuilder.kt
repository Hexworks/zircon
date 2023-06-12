package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.ScrollBar
import org.hexworks.zircon.api.component.builder.base.BaseContainerBuilder
import org.hexworks.zircon.api.dsl.buildChildFor
import org.hexworks.zircon.internal.component.impl.DefaultVerticalScrollBar
import org.hexworks.zircon.internal.component.renderer.VerticalScrollBarRenderer
import org.hexworks.zircon.internal.dsl.ZirconDsl

/**
 * Builder for a vertical [ScrollBar]. By default, it creates a [ScrollBar] with
 * - [itemsShownAtOnce]: `1`
 * - [numberOfScrollableItems]: `100`
 */
@ZirconDsl
class VerticalScrollBarBuilder :
    ScrollBarBuilder<ScrollBar>(VerticalScrollBarRenderer()) {

    override fun build(): ScrollBar = DefaultVerticalScrollBar(
        componentMetadata = createMetadata(),
        renderingStrategy = createRenderingStrategy(),
        minValue = 0,
        maxValue = numberOfScrollableItems,
        itemsShownAtOnce = itemsShownAtOnce,
        numberOfSteps = size.height,
    ).attachListeners()
}

/**
 * Creates a new [ScrollBar] using the component builder DSL and returns it.
 */
fun buildVerticalScrollBar(init: VerticalScrollBarBuilder.() -> Unit): ScrollBar =
    VerticalScrollBarBuilder().apply(init).build()

/**
 * Creates a new [ScrollBar] using the component builder DSL, adds it to the
 * receiver [BaseContainerBuilder] it and returns the [ScrollBar].
 */
fun <T : BaseContainerBuilder<*>> T.verticalScrollBar(
    init: VerticalScrollBarBuilder.() -> Unit
): ScrollBar = buildChildFor(this, VerticalScrollBarBuilder(), init)
