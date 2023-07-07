package org.hexworks.zircon.internal.component.impl

import org.hexworks.cobalt.core.api.extensions.abbreviate
import org.hexworks.cobalt.databinding.api.event.ObservableValueChanged
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.ScrollBar
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.data.ComponentState
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.extensions.whenEnabledRespondWith
import org.hexworks.zircon.api.uievent.*
import kotlin.math.ceil
import kotlin.math.roundToInt
import kotlin.math.truncate

@Suppress("LeakingThis")
abstract class BaseScrollBar(
    final override val minValue: Int,
    final override var maxValue: Int,
    final override val numberOfSteps: Int,
    final override var itemsShownAtOnce: Int,
    componentMetadata: ComponentMetadata,
    renderer: ComponentRenderingStrategy<ScrollBar>
) : ScrollBar, DefaultComponent(
    metadata = componentMetadata,
    renderer = renderer
) {

    private var range: Int = maxValue - minValue
    protected var valuePerStep: Double = range.toDouble() / numberOfSteps.toDouble()

    final override val currentValueProperty = minValue.toProperty()
    final override var currentValue: Int by currentValueProperty.asDelegate()

    final override val currentStepProperty = minValue.toProperty()
    final override var currentStep: Int by currentStepProperty.asDelegate()

    override var barSizeInSteps = (itemsShownAtOnce / valuePerStep).roundToInt()

    init {
        currentValueProperty.onChange {
            computeCurrentStep(it.newValue)
        }
        disabledProperty.onChange {
            if (it.newValue) {
                LOGGER.debug { "Disabling ScrollBar (id=${id.abbreviate()}, disabled=$isDisabled)." }
                componentState = ComponentState.DISABLED
            } else {
                LOGGER.debug { "Enabling ScrollBar (id=${id.abbreviate()}, disabled=$isDisabled)." }
                componentState = ComponentState.DEFAULT
            }
        }
    }

    private fun computeCurrentStep(newValue: Int) {
        val actualValue = newValue.coerceIn(minValue..maxValue)
        val actualStep = actualValue.toDouble() / valuePerStep
        val roundedStep = truncate(actualStep)
        currentStep = roundedStep.toInt()
    }

    override fun incrementValues() {
        if (currentValue + itemsShownAtOnce < maxValue) {
            currentValue++
        }
    }

    override fun decrementValues() {
        if (currentValue > minValue) {
            currentValue--
        }
    }

    override fun incrementStep() {
        if (currentStep + barSizeInSteps < numberOfSteps) {
            computeValueToClosestOfStep(currentStep + 1)
        } else {
            // Try to increase by partial step, so we can always get to the last partial page of items
            incrementValues()
        }
    }

    override fun decrementStep() {
        if (currentStep - 1 >= 0) {
            computeValueToClosestOfStep(currentStep - 1)
        }
    }

    override fun resizeScrollBar(maxValue: Int) {
        if (maxValue >= 0) {
            this.maxValue = maxValue
            range = this.maxValue - minValue
            valuePerStep = range.toDouble() / numberOfSteps.toDouble()

            barSizeInSteps = ceil(itemsShownAtOnce / valuePerStep).toInt()
            addToCurrentValues(0)
        }
    }

    protected fun addToCurrentValues(value: Int) {
        if (currentValue + itemsShownAtOnce + value <= maxValue) {
            currentValue += value
        } else {
            currentValue = maxValue - itemsShownAtOnce
        }
    }

    protected fun subtractToCurrentValues(value: Int) {
        if (currentValue - value > minValue) {
            currentValue -= value
        } else {
            currentValue = minValue
        }
    }

    private fun computeValueToClosestOfStep(step: Int) {
        val actualStep = when {
            step < 0 -> 0
            step + barSizeInSteps > numberOfSteps -> numberOfSteps - barSizeInSteps
            else -> step
        }
        val calculatedValue = (actualStep * valuePerStep) + minValue
        currentValue = ceil(calculatedValue).toInt()
    }

    private fun computeClickedZone(clickPosition: Int): ClickedZone {
        return when {
            clickPosition < currentStep -> Companion.ClickedZone.TowardsLow
            clickPosition > currentStep + barSizeInSteps -> Companion.ClickedZone.TowardsHigh
            else -> Companion.ClickedZone.InsideBar
        }
    }

    abstract fun getMousePosition(event: MouseEvent): Int

    override fun mousePressed(event: MouseEvent, phase: UIEventPhase) = whenEnabledRespondWith {
        if (phase == UIEventPhase.TARGET) {
            LOGGER.debug { "ScrollBar (id=${id.abbreviate()}, disabled=$isDisabled) was mouse pressed." }
            componentState = ComponentState.ACTIVE

            when (computeClickedZone(getMousePosition(event))) {
                Companion.ClickedZone.TowardsLow -> decrementStep()
                Companion.ClickedZone.TowardsHigh -> incrementStep()
                else -> {
                }
            }

            Processed
        } else Pass
    }

    override fun mouseDragged(event: MouseEvent, phase: UIEventPhase) = whenEnabledRespondWith {
        if (phase == UIEventPhase.TARGET) {
            LOGGER.debug { "ScrollBar (id=${id.abbreviate()}, disabled=$isDisabled) was mouse pressed." }
            componentState = ComponentState.ACTIVE

            val mousePosition = getMousePosition(event)
            computeValueToClosestOfStep(mousePosition - (barSizeInSteps / 2))

            Processed
        } else Pass
    }

    override fun mouseWheelRotatedUp(event: MouseEvent, phase: UIEventPhase): UIEventResponse {
        if (phase != UIEventPhase.TARGET) return Pass
        val originalValue = currentValue
        decrementStep()
        return if (currentValue != originalValue) Processed else Pass
    }

    override fun mouseWheelRotatedDown(event: MouseEvent, phase: UIEventPhase): UIEventResponse {
        if (phase != UIEventPhase.TARGET) return Pass
        val originalValue = currentValue
        incrementStep()
        return if (currentValue != originalValue) Processed else Pass
    }

    abstract override fun keyPressed(event: KeyboardEvent, phase: UIEventPhase): UIEventResponse

    override fun activated() = whenEnabledRespondWith {
        if (isDisabled) {
            LOGGER.warn { "Trying to activate disabled ScrollBar (id=${id.abbreviate()}. Request dropped." }
            Pass
        } else {
            LOGGER.debug { "ScrollBar (id=${id.abbreviate()}, disabled=$isDisabled) was activated." }
            componentState = ComponentState.HIGHLIGHTED
            Processed
        }
    }

    override fun convertColorTheme(colorTheme: ColorTheme) = ComponentStyleSetBuilder.newBuilder()
        .withDefaultStyle(
            StyleSetBuilder.newBuilder()
                .withForegroundColor(colorTheme.primaryForegroundColor)
                .withBackgroundColor(TileColor.transparent())
                .build()
        )
        .withHighlightedStyle(
            StyleSetBuilder.newBuilder()
                .withForegroundColor(colorTheme.primaryBackgroundColor)
                .withBackgroundColor(colorTheme.accentColor)
                .build()
        )
        .withDisabledStyle(
            StyleSetBuilder.newBuilder()
                .withForegroundColor(colorTheme.secondaryForegroundColor)
                .withBackgroundColor(colorTheme.secondaryBackgroundColor)
                .build()
        )
        .withFocusedStyle(
            StyleSetBuilder.newBuilder()
                .withForegroundColor(colorTheme.primaryBackgroundColor)
                .withBackgroundColor(colorTheme.primaryForegroundColor)
                .build()
        )
        .build()

    override fun onValueChange(fn: (ObservableValueChanged<Int>) -> Unit): Subscription {
        return currentValueProperty.onChange(fn)
    }

    override fun onStepChange(fn: (ObservableValueChanged<Int>) -> Unit): Subscription {
        return currentStepProperty.onChange(fn)
    }

    companion object {
        val LOGGER = LoggerFactory.getLogger(ScrollBar::class)

        enum class ClickedZone {
            TowardsLow,
            InsideBar,
            TowardsHigh
        }
    }
}
