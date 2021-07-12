package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.ProgressBar
import org.hexworks.zircon.api.component.builder.base.BaseComponentBuilder
import org.hexworks.zircon.internal.component.impl.DefaultProgressBar
import org.hexworks.zircon.internal.component.renderer.DefaultProgressBarRenderer
import org.hexworks.zircon.internal.dsl.ZirconDsl
import kotlin.jvm.JvmStatic

@Suppress("UNCHECKED_CAST", "MemberVisibilityCanBePrivate")
/**
 * Builder for the progress bar. By default, it creates a progress bar with a maxValue of 100 and 10 steps.
 */
@ZirconDsl
class ProgressBarBuilder : BaseComponentBuilder<ProgressBar, ProgressBarBuilder>(DefaultProgressBarRenderer()) {

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

    fun withRange(range: Int) = also {
        this.range = range
    }

    fun withNumberOfSteps(steps: Int) = also {
        this.numberOfSteps = steps
    }

    fun withDisplayPercentValueOfProgress(displayPercentValueOfProgress: Boolean) = also {
        this.displayPercentValueOfProgress = displayPercentValueOfProgress
    }

    override fun build(): ProgressBar {
        return DefaultProgressBar(
            componentMetadata = createMetadata(),
            renderingStrategy = createRenderingStrategy(),
            range = range,
            numberOfSteps = numberOfSteps,
            displayPercentValueOfProgress = displayPercentValueOfProgress,
        ).attachListeners()
    }

    override fun createCopy() = newBuilder()
        .withProps(props.copy())
        .withRange(range)
        .withNumberOfSteps(numberOfSteps)
        .withDisplayPercentValueOfProgress(displayPercentValueOfProgress)

    companion object {

        @JvmStatic
        fun newBuilder() = ProgressBarBuilder()
    }
}
