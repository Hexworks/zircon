package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.ProgressBar
import org.hexworks.zircon.api.component.builder.base.BaseComponentBuilder
import org.hexworks.zircon.api.component.builder.base.BaseContainerBuilder
import org.hexworks.zircon.api.dsl.buildChildFor
import org.hexworks.zircon.internal.component.impl.DefaultProgressBar
import org.hexworks.zircon.internal.component.renderer.DefaultProgressBarRenderer
import org.hexworks.zircon.internal.dsl.ZirconDsl

/**
 * Builder for the progress bar. By default, it creates a progress bar with a maxValue of 100 and 10 steps.
 */
@ZirconDsl
class ProgressBarBuilder :
    BaseComponentBuilder<ProgressBar>(
        initialRenderer = DefaultProgressBarRenderer()
    ) {

    var range: Int = 100
        set(value) {
            require(value > 0) { "Range must be greater 0" }
            field = value
        }

    var numberOfSteps: Int = 10
        set(value) {
            require(value in 1..range) { "Number of steps must be greater 0 and smaller than the maxValue" }
            field = value
            fixContentSizeFor(value)
        }

    var displayPercentValueOfProgress: Boolean = false

    override fun build(): ProgressBar {
        return DefaultProgressBar(
            componentMetadata = createMetadata(),
            renderingStrategy = createRenderingStrategy(),
            range = range,
            numberOfSteps = numberOfSteps,
            displayPercentValueOfProgress = displayPercentValueOfProgress,
        ).attachListeners()
    }
}

/**
 * Creates a new [ProgressBar] using the component builder DSL and returns it.
 */
fun buildProgressBar(init: ProgressBarBuilder.() -> Unit): ProgressBar =
    ProgressBarBuilder().apply(init).build()

/**
 * Creates a new [ProgressBar] using the component builder DSL, adds it to the
 * receiver [BaseContainerBuilder] it and returns the [ProgressBar].
 */
fun <T : BaseContainerBuilder<*>> T.progressBar(
    init: ProgressBarBuilder.() -> Unit
): ProgressBar = buildChildFor(this, ProgressBarBuilder(), init)
