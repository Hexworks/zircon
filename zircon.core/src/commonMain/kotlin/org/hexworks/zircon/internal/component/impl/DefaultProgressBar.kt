package org.hexworks.zircon.internal.component.impl

import org.hexworks.cobalt.databinding.api.createPropertyFrom
import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.ProgressBar
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import kotlin.math.min
import kotlin.math.roundToInt

class DefaultProgressBar(componentMetadata: ComponentMetadata,
                         private val renderingStrategy: ComponentRenderingStrategy<ProgressBar>,
                         override val range: Int,
                         override val numberOfSteps: Int,
                         override val displayPercentValueOfProgress: Boolean)
    : ProgressBar, DefaultComponent(
        componentMetadata = componentMetadata,
        renderer = renderingStrategy) {

    override val progressProperty = createPropertyFrom(0.0)
    override var progress: Double by progressProperty.asDelegate()

    init {
        render()
        progressProperty.onChange {
            render()
        }
    }


    override fun acceptsFocus() = false

    override fun convertColorTheme(colorTheme: ColorTheme) = ComponentStyleSetBuilder.newBuilder()
            .withDefaultStyle(StyleSetBuilder.newBuilder()
                    .withForegroundColor(colorTheme.secondaryForegroundColor)
                    .withBackgroundColor(TileColor.transparent())
                    .build())
            .build()

    fun getProgressBarState(): ProgressBarState {
        val currentProgress = min(range.toDouble(), progress)
        val currentProgressInPercent = ((currentProgress / range.toDouble()) * 100).roundToInt()
        val currentStep = ((currentProgress / range.toDouble()) * numberOfSteps).toInt()
        val currentStepWidth = ((contentSize.width / numberOfSteps.toDouble()) * currentStep).roundToInt()
        return ProgressBarState(currentStepWidth, currentProgressInPercent)
    }

    data class ProgressBarState(val width: Int, val currentProgressInPercent: Int)
}
