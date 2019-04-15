package org.hexworks.zircon.internal.component.impl

import org.hexworks.cobalt.databinding.api.createPropertyFrom
import org.hexworks.cobalt.databinding.api.extensions.onChange
import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.Label
import org.hexworks.zircon.api.component.ProgressBar
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.extensions.abbreviate
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt

class DefaultProgressBar(componentMetadata: ComponentMetadata,
                         private val renderingStrategy: ComponentRenderingStrategy<ProgressBar>,
                         override val range: Int,
                         override val numberOfSteps: Int,
                         override val displayPercentValueOfProgress: Boolean
) : ProgressBar, DefaultComponent(
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

    override fun applyColorTheme(colorTheme: ColorTheme): ComponentStyleSet {
        LOGGER.debug("Applying color theme ($colorTheme) to Label (id=${id.abbreviate()}).")
        return ComponentStyleSetBuilder.newBuilder()
                .withDefaultStyle(StyleSetBuilder.newBuilder()
                        .withForegroundColor(colorTheme.secondaryForegroundColor)
                        .withBackgroundColor(TileColor.transparent())
                        .build())
                .build().also {
                    componentStyleSet = it
                    render()
                }
    }

    fun getProgressBarState(): ProgressBarState {
        val currentProgress = min(range.toDouble(), progress)
        val currentStep = ((currentProgress / range.toDouble()) * numberOfSteps).toInt()
        val currentStepInPercent = ((currentProgress / range.toDouble()) * 100).roundToInt()
        return ProgressBarState(currentStep, currentStepInPercent)
    }


    override fun render() {
        LOGGER.debug("Label (id=${id.abbreviate()}, visibility=$visibility) was rendered.")
        renderingStrategy.render(this, graphics)
    }

    companion object {
        val LOGGER = LoggerFactory.getLogger(Label::class)
    }

    data class ProgressBarState(val width: Int, val currentProgressInPercent: Int)
}
