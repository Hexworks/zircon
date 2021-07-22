package org.hexworks.zircon.internal.component.impl

import org.hexworks.cobalt.databinding.api.event.ObservableValueChanged
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.Slider
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.data.ComponentState
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.extensions.abbreviate
import org.hexworks.zircon.api.extensions.whenEnabledRespondWith
import org.hexworks.zircon.api.uievent.*
import kotlin.math.roundToInt
import kotlin.math.truncate

abstract class BaseSlider(
    final override val minValue: Int,
    final override val maxValue: Int,
    final override val numberOfSteps: Int,
    componentMetadata: ComponentMetadata,
    renderer: ComponentRenderingStrategy<Slider>
) : Slider, DefaultComponent(
    metadata = componentMetadata,
    renderer = renderer
) {

    private val range: Int = maxValue - minValue
    protected val valuePerStep: Double = range.toDouble() / numberOfSteps.toDouble()

    final override val currentValueProperty = minValue.toProperty()
    override var currentValue: Int by currentValueProperty.asDelegate()

    final override val currentStepProperty = 0.toProperty()
    override var currentStep: Int by currentStepProperty.asDelegate()

    init {
        currentValueProperty.onChange {
            computeCurrentStep(it.newValue)
        }
        disabledProperty.onChange {
            componentState = if (it.newValue) {
                LOGGER.debug("Disabling Slider (id=${id.abbreviate()}, disabled=$isDisabled).")
                ComponentState.DISABLED
            } else {
                LOGGER.debug("Enabling Slider (id=${id.abbreviate()}, disabled=$isDisabled).")
                ComponentState.DEFAULT
            }
        }
    }

    private fun computeCurrentStep(newValue: Int) {
        val actualValue = when {
            newValue > maxValue -> maxValue
            newValue < minValue -> minValue
            else -> newValue
        }
        val actualStep = actualValue.toDouble() / valuePerStep
        val roundedStep = truncate(actualStep)
        currentStep = roundedStep.toInt()
    }

    override fun incrementCurrentValue() {
        if (currentValue < maxValue) {
            currentValue++
        }
    }

    override fun decrementCurrentValue() {
        if (currentValue > minValue) {
            currentValue--
        }
    }

    override fun incrementCurrentStep() {
        if (currentStep + 1 < numberOfSteps) {
            setValueToClosestOfStep(currentStep + 1)
        }
    }

    override fun decrementCurrentStep() {
        if (currentStep - 1 > 0) {
            setValueToClosestOfStep(currentStep - 1)
        }
    }

    protected fun addToCurrentValue(value: Int) {
        if (currentValue + value <= maxValue) {
            currentValue += value
        } else {
            currentValue = maxValue
        }
    }

    protected fun subtractToCurrentValue(value: Int) {
        if (currentValue - value > minValue) {
            currentValue -= value
        } else {
            currentValue = minValue
        }
    }

    private fun setValueToClosestOfStep(step: Int) {
        val actualStep = when {
            step < 0 -> 0
            step > numberOfSteps -> numberOfSteps
            else -> step
        }
        val calculatedValue = (actualStep * valuePerStep) + minValue
        currentValue = calculatedValue.roundToInt()
    }

    abstract fun getMousePosition(event: MouseEvent): Int

    override fun mousePressed(event: MouseEvent, phase: UIEventPhase) = whenEnabledRespondWith {
        if (phase == UIEventPhase.TARGET) {
            LOGGER.debug("Gutter (id=${id.abbreviate()}, disabled=$isDisabled) was mouse pressed.")
            componentState = ComponentState.ACTIVE
            setValueToClosestOfStep(getMousePosition(event))
            Processed
        } else Pass
    }

    override fun mouseDragged(event: MouseEvent, phase: UIEventPhase) = whenEnabledRespondWith {
        if (phase == UIEventPhase.TARGET) {
            LOGGER.debug("Gutter (id=${id.abbreviate()}, disabled=$isDisabled) was mouse pressed.")
            componentState = ComponentState.ACTIVE
            setValueToClosestOfStep(getMousePosition(event))
            Processed
        } else Pass
    }

    abstract override fun keyPressed(event: KeyboardEvent, phase: UIEventPhase): UIEventResponse

    override fun activated() = whenEnabledRespondWith {
        if (isDisabled) {
            LOGGER.warn("Trying to activate disabled Gutter (id=${id.abbreviate()}. Request dropped.")
            Pass
        } else {
            LOGGER.debug("Gutter (id=${id.abbreviate()}, disabled=$isDisabled) was activated.")
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
        val LOGGER = LoggerFactory.getLogger(Slider::class)
    }
}
