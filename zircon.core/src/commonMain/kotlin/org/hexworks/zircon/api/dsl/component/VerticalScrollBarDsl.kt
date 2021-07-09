package org.hexworks.zircon.api.dsl.component

import org.hexworks.zircon.api.builder.component.VerticalScrollBarBuilder
import org.hexworks.zircon.api.component.ScrollBar
import org.hexworks.zircon.api.component.builder.base.BaseContainerBuilder

/**
 * Creates a new [ScrollBar] using the component builder DSL and returns it.
 */
fun buildVerticalScrollBar(init: VerticalScrollBarBuilder.() -> Unit): ScrollBar =
    VerticalScrollBarBuilder().apply(init).build()

/**
 * Creates a new [ScrollBar] using the component builder DSL, adds it to the
 * receiver [BaseContainerBuilder] it and returns the [ScrollBar].
 */
fun <T : BaseContainerBuilder<*, *>> T.verticalScrollBar(
    init: VerticalScrollBarBuilder.() -> Unit
): ScrollBar = buildChildFor(this, VerticalScrollBarBuilder(), init)
