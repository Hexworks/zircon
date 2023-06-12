package org.hexworks.zircon.internal.component.impl

import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.zircon.api.builder.component.componentStyleSet
import org.hexworks.zircon.api.builder.graphics.styleSet
import org.hexworks.zircon.api.color.TileColor.Companion.transparent
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.ProgressBar
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import kotlin.math.min
import kotlin.math.roundToInt

class DefaultProgressBar internal constructor(
    componentMetadata: ComponentMetadata,
    renderingStrategy: ComponentRenderingStrategy<ProgressBar>,
    override val range: Int,
    override val numberOfSteps: Int,
    override val displayPercentValueOfProgress: Boolean
) : ProgressBar, DefaultComponent(
    metadata = componentMetadata,
    renderer = renderingStrategy
) {

    override val progressProperty = 0.0.toProperty()
    override var progress: Double by progressProperty.asDelegate()

    override fun acceptsFocus() = false

    override fun convertColorTheme(colorTheme: ColorTheme) = componentStyleSet {
        defaultStyle = styleSet {
            foregroundColor = colorTheme.secondaryForegroundColor
            backgroundColor = transparent()
        }
    }

    fun getProgressBarState(): ProgressBarState {
        val currentProgress = min(range.toDouble(), progress)
        val currentProgressInPercent = ((currentProgress / range.toDouble()) * 100).roundToInt()
        val currentStep = ((currentProgress / range.toDouble()) * numberOfSteps).toInt()
        val currentProgression = ((contentSize.width / numberOfSteps.toDouble()) * currentStep).roundToInt()
        return ProgressBarState(currentProgression, currentProgressInPercent)
    }

    data class ProgressBarState(val currentProgression: Int, val currentProgressInPercent: Int)
}
