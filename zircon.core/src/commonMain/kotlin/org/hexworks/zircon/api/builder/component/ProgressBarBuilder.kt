package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.component.ProgressBar
import org.hexworks.zircon.api.component.builder.base.BaseComponentBuilder
import org.hexworks.zircon.api.component.data.CommonComponentProperties
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.internal.component.renderer.DefaultComponentRenderingStrategy
import org.hexworks.zircon.internal.component.impl.DefaultProgressBar
import org.hexworks.zircon.internal.component.renderer.DefaultProgressBarRenderer
import kotlin.jvm.JvmStatic
import kotlin.math.max

@Suppress("UNCHECKED_CAST")
/**
 * Builder for the progress bar. By default, it creates a progress bar with a maxValue of 100 and 10 steps.
 */
data class ProgressBarBuilder(
        private var range: Int = 100,
        private var numberOfSteps: Int = 10,
        private var displayPercentValueOfProgress: Boolean = false,
        override var props: CommonComponentProperties<ProgressBar> = CommonComponentProperties(
                componentRenderer = DefaultProgressBarRenderer()))
    : BaseComponentBuilder<ProgressBar, ProgressBarBuilder>() {

    fun withRange(range: Int) = also {
        require(range > 0) { "Range must be greater 0" }
        this.range = range
    }

    fun withNumberOfSteps(steps: Int) = also {
        require(steps in 1..range) { "Number of steps must be greater 0 and smaller than the maxValue" }
        this.numberOfSteps = steps
        contentSize = contentSize
                .withWidth(max(steps, contentSize.width))
    }

    fun withDisplayPercentValueOfProgress(displayPercentValueOfProgress: Boolean) = also {
        this.displayPercentValueOfProgress = displayPercentValueOfProgress
    }


    override fun build(): ProgressBar {
        return DefaultProgressBar(
                componentMetadata = ComponentMetadata(
                        size = size,
                        relativePosition = position,
                        componentStyleSet = componentStyleSet,
                        tileset = tileset),
                range = range,
                numberOfSteps = numberOfSteps,
                displayPercentValueOfProgress = displayPercentValueOfProgress,
                renderingStrategy = DefaultComponentRenderingStrategy(
                        decorationRenderers = decorationRenderers,
                        componentRenderer = props.componentRenderer as ComponentRenderer<ProgressBar>)).also {
            if(colorTheme !== ColorThemes.default()) {
                it.theme = colorTheme
            }
        }
    }

    override fun createCopy() = copy(props = props.copy())

    companion object {

        @JvmStatic
        fun newBuilder() = ProgressBarBuilder()
    }
}
