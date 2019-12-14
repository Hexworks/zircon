package org.hexworks.zircon.internal.component.impl

import org.hexworks.cobalt.databinding.api.createPropertyFrom
import org.hexworks.cobalt.databinding.api.event.ChangeEvent
import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.behavior.Disablable
import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.Slider
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.extensions.abbreviate
import org.hexworks.zircon.api.extensions.whenEnabled
import org.hexworks.zircon.api.extensions.whenEnabledRespondWith
import org.hexworks.zircon.api.uievent.KeyboardEvent
import org.hexworks.zircon.api.uievent.MouseEvent
import org.hexworks.zircon.api.uievent.Pass
import org.hexworks.zircon.api.uievent.Processed
import org.hexworks.zircon.api.uievent.UIEventPhase
import org.hexworks.zircon.api.uievent.UIEventResponse
import kotlin.math.roundToInt
import kotlin.math.truncate

abstract class BaseSlider(final override val minValue: Int,
                          final override val maxValue: Int,
                          final override val numberOfSteps: Int,
                          componentMetadata: ComponentMetadata,
                          private val renderingStrategy: ComponentRenderingStrategy<Slider>) :
        Slider, DefaultComponent(
        componentMetadata = componentMetadata,
        renderer = renderingStrategy),
        Disablable by Disablable.create() {

    private val range: Int = maxValue - minValue
    protected val valuePerStep: Double = range.toDouble() / numberOfSteps.toDouble()

    final override val currentValueProperty = createPropertyFrom(minValue)
    override var currentValue: Int by currentValueProperty.asDelegate()

    final override val currentStepProperty = createPropertyFrom(0)
    override var currentStep: Int by currentStepProperty.asDelegate()

    init {
        render()
        currentValueProperty.onChange {
            computeCurrentStep(it.newValue)
            render()
        }
        currentStepProperty.onChange {
            render()
        }
        disabledProperty.onChange {
            if (it.newValue) {
                LOGGER.debug("Disabling Slider (id=${id.abbreviate()}, disabled=$isDisabled).")
                componentStyleSet.applyDisabledStyle()
            } else {
                LOGGER.debug("Enabling Slider (id=${id.abbreviate()}, disabled=$isDisabled).")
                componentStyleSet.reset()
            }
            render()
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

    override fun acceptsFocus() = isDisabled.not()

    override fun focusGiven() = whenEnabled {
        LOGGER.debug("Gutter (id=${id.abbreviate()}, disabled=$isDisabled) was given focus.")
        componentStyleSet.applyFocusedStyle()
        render()
    }

    override fun focusTaken() = whenEnabled {
        LOGGER.debug("Gutter (id=${id.abbreviate()}, disabled=$isDisabled) lost focus.")
        componentStyleSet.reset()
        render()
    }

    override fun mouseEntered(event: MouseEvent, phase: UIEventPhase) = whenEnabledRespondWith {
        if (phase == UIEventPhase.TARGET) {
            LOGGER.debug("Gutter (id=${id.abbreviate()}, disabled=$isDisabled) was mouse entered.")
            componentStyleSet.applyMouseOverStyle()
            render()
            Processed
        } else Pass
    }

    override fun mouseExited(event: MouseEvent, phase: UIEventPhase) = whenEnabledRespondWith {
        if (phase == UIEventPhase.TARGET) {
            LOGGER.debug("Gutter (id=${id.abbreviate()}, disabled=$isDisabled) was mouse exited.")
            componentStyleSet.reset()
            render()
            Processed
        } else Pass
    }

    override fun mousePressed(event: MouseEvent, phase: UIEventPhase) = whenEnabledRespondWith {
        if (phase == UIEventPhase.TARGET) {
            LOGGER.debug("Gutter (id=${id.abbreviate()}, disabled=$isDisabled) was mouse pressed.")
            componentStyleSet.applyActiveStyle()
            setValueToClosestOfStep(getMousePosition(event))
            render()
            Processed
        } else Pass
    }

    override fun mouseReleased(event: MouseEvent, phase: UIEventPhase) = whenEnabledRespondWith {
        if (phase == UIEventPhase.TARGET) {
            LOGGER.debug("Gutter (id=${id.abbreviate()}, disabled=$isDisabled) was mouse released.")
            componentStyleSet.applyMouseOverStyle()
            render()
            Processed
        } else Pass
    }

    override fun mouseDragged(event: MouseEvent, phase: UIEventPhase) = whenEnabledRespondWith {
        if (phase == UIEventPhase.TARGET) {
            LOGGER.debug("Gutter (id=${id.abbreviate()}, disabled=$isDisabled) was mouse pressed.")
            componentStyleSet.applyActiveStyle()
            setValueToClosestOfStep(getMousePosition(event))
            render()
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
            componentStyleSet.applyMouseOverStyle()
            render()
            Processed
        }
    }

    override fun convertColorTheme(colorTheme: ColorTheme): ComponentStyleSet {
        LOGGER.debug("Applying color theme: $colorTheme to Gutter (id=${id.abbreviate()}, disabled=$isDisabled).")
        return ComponentStyleSetBuilder.newBuilder()
                .withDefaultStyle(StyleSetBuilder.newBuilder()
                        .withForegroundColor(colorTheme.primaryForegroundColor)
                        .withBackgroundColor(TileColor.transparent())
                        .build())
                .withMouseOverStyle(StyleSetBuilder.newBuilder()
                        .withForegroundColor(colorTheme.primaryBackgroundColor)
                        .withBackgroundColor(colorTheme.accentColor)
                        .build())
                .withDisabledStyle(StyleSetBuilder.newBuilder()
                        .withForegroundColor(colorTheme.secondaryForegroundColor)
                        .withBackgroundColor(colorTheme.secondaryBackgroundColor)
                        .build())
                .withFocusedStyle(StyleSetBuilder.newBuilder()
                        .withForegroundColor(colorTheme.primaryBackgroundColor)
                        .withBackgroundColor(colorTheme.primaryForegroundColor)
                        .build())
                .build().also {
                    componentStyleSet = it
                    render()
                }
    }

    final override fun render() {
        LOGGER.debug("Slider (id=${id.abbreviate()}, disabled=$isDisabled, hidden=$isHidden) was rendered.")
        renderingStrategy.render(this, graphics)
    }

    override fun onValueChange(fn: (ChangeEvent<Int>) -> Unit): Subscription {
        return currentValueProperty.onChange(fn)
    }

    override fun onStepChange(fn: (ChangeEvent<Int>) -> Unit): Subscription {
        return currentStepProperty.onChange(fn)
    }

    companion object {
        val LOGGER = LoggerFactory.getLogger(Slider::class)
    }
}
