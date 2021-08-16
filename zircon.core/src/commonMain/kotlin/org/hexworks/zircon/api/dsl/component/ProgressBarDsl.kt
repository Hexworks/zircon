package org.hexworks.zircon.api.dsl.component

import org.hexworks.zircon.api.builder.component.ProgressBarBuilder
import org.hexworks.zircon.api.component.Container
import org.hexworks.zircon.api.component.ProgressBar
import org.hexworks.zircon.api.component.builder.base.BaseContainerBuilder

/**
 * Creates a new [ProgressBar] using the component builder DSL and returns it.
 */
fun buildProgressBar(init: ProgressBarBuilder.() -> Unit): ProgressBar =
    ProgressBarBuilder.newBuilder().apply(init).build()

/**
 * Creates a new [ProgressBar] using the component builder DSL, adds it to the
 * receiver [BaseContainerBuilder] it and returns the [ProgressBar].
 */
fun <T : BaseContainerBuilder<*, *>> T.progressBar(
    init: ProgressBarBuilder.() -> Unit
): ProgressBar = buildChildFor(this, ProgressBarBuilder.newBuilder(), init)
