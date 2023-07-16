package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.ScrollBar
import org.hexworks.zircon.api.component.builder.base.BaseContainerBuilder
import org.hexworks.zircon.api.dsl.buildChildFor
import org.hexworks.zircon.internal.component.impl.DefaultHorizontalScrollBar
import org.hexworks.zircon.internal.component.renderer.HorizontalScrollBarRenderer
import org.hexworks.zircon.internal.dsl.ZirconDsl

/**
 * Builder for a horizontal [ScrollBar]. By default, it creates a [ScrollBar] with
 * - [itemsShownAtOnce]: `1`
 * - [numberOfScrollableItems]: `100`
 */
@ZirconDsl
class HorizontalScrollBarBuilder :
    ScrollBarBuilder<ScrollBar>(HorizontalScrollBarRenderer()) {

    override fun build(): ScrollBar = DefaultHorizontalScrollBar(
        componentMetadata = createMetadata(),
        renderingStrategy = createRenderingStrategy(),
        minValue = 0,
        maxValue = numberOfScrollableItems,
        itemsShownAtOnce = size.width,
        numberOfSteps = size.width,
    ).attachListeners()
}

/**
 * Creates a new [ScrollBar] using the component builder DSL and returns it.
 */
fun buildHorizontalScrollBar(
    init: HorizontalScrollBarBuilder.() -> Unit
): ScrollBar = HorizontalScrollBarBuilder().apply(init).build()

/**
 * Creates a new [ScrollBar] using the component builder DSL, adds it to the
 * receiver [BaseContainerBuilder] it and returns the [ScrollBar].
 */
fun <T : BaseContainerBuilder<*>> T.horizontalScrollBar(
    init: HorizontalScrollBarBuilder.() -> Unit
): ScrollBar = buildChildFor(this, HorizontalScrollBarBuilder(), init)
