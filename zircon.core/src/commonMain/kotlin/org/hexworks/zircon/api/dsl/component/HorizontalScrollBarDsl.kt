package org.hexworks.zircon.api.dsl.component

import org.hexworks.zircon.api.builder.component.HorizontalScrollBarBuilder
import org.hexworks.zircon.api.component.Container
import org.hexworks.zircon.api.component.ScrollBar
import org.hexworks.zircon.api.component.builder.base.BaseContainerBuilder

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
fun <T : BaseContainerBuilder<*, *>> T.horizontalScrollBar(
        init: HorizontalScrollBarBuilder.() -> Unit
): ScrollBar = buildChildFor(this, HorizontalScrollBarBuilder(), init)