package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.ProgressBar
import org.hexworks.zircon.api.component.base.BaseComponentBuilder
import org.hexworks.zircon.api.component.data.CommonComponentProperties
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.component.renderer.impl.DefaultComponentRenderingStrategy
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.internal.component.impl.DefaultProgressBar
import org.hexworks.zircon.internal.component.renderer.DefaultProgressBarRenderer
import kotlin.jvm.JvmStatic

@Suppress("UNCHECKED_CAST")
/**
 * Builder for the progress bar. By default, it creates a progress bar with a range of 100 and 10 steps.
 */
data class ProgressBarBuilder(
        private var range: Int = 100,
        private var numberOfSteps: Int = 10,
        private var displayPercentValueOfProgress: Boolean = false,
        private var commonComponentProperties: CommonComponentProperties<ProgressBar> = CommonComponentProperties(
                componentRenderer = DefaultProgressBarRenderer()))
    : BaseComponentBuilder<ProgressBar, ProgressBarBuilder>(commonComponentProperties) {


    override fun withTitle(title: String): ProgressBarBuilder {
        throw UnsupportedOperationException("You can't set a title for a progress bar")
    }

    fun withRange(range: Int) = also {
        require(range > 0) { "Range must be greater 0" }
        this.range = range
    }

    fun withNumberOfSteps(steps: Int) = also {
        require(steps in 1..range) { "Number of steps must be greater 0 and smaller than the range" }
        this.numberOfSteps = steps
    }

    fun withDisplayPercentValueOfProgress(displayPercentValueOfProgress: Boolean) = also {
        this.displayPercentValueOfProgress = displayPercentValueOfProgress
    }


    override fun build(): ProgressBar {
        fillMissingValues()
        val finalSize = if (size.isUnknown()) {
            determineSize()
        } else {
            size
        }

        return DefaultProgressBar(
                componentMetadata = ComponentMetadata(
                        size = finalSize,
                        position = fixPosition(finalSize),
                        componentStyleSet = componentStyleSet,
                        tileset = tileset),
                range = range,
                numberOfSteps = numberOfSteps,
                displayPercentValueOfProgress = displayPercentValueOfProgress,
                renderingStrategy = DefaultComponentRenderingStrategy(
                        decorationRenderers = decorationRenderers,
                        componentRenderer = commonComponentProperties.componentRenderer as ComponentRenderer<ProgressBar>))
    }

    override fun createCopy() = copy(commonComponentProperties = commonComponentProperties.copy())

    private fun determineSize(): Size {
        val coreSize = decorationRenderers.asSequence()
                .map { it.occupiedSize }
                .fold(Size.create(numberOfSteps, 1), Size::plus)

        return if (displayPercentValueOfProgress)
            coreSize + Size.create(5, 0) // blank + three digits + %
        else
            coreSize

    }

    companion object {

        @JvmStatic
        fun newBuilder() = ProgressBarBuilder()
    }
}
