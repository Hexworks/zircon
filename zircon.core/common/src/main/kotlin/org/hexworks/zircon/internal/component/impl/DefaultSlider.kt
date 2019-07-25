package org.hexworks.zircon.internal.component.impl

import org.hexworks.cobalt.databinding.api.createPropertyFrom
import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.behavior.ChangeListener
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
import org.hexworks.zircon.api.uievent.*
import kotlin.math.min
import kotlin.math.roundToInt
import kotlin.math.truncate

abstract class DefaultSlider(override val range: Int,
                             override val numberOfSteps: Int,
                             componentMetadata: ComponentMetadata,
                             private val renderingStrategy: ComponentRenderingStrategy<Slider>) :
    Slider, DefaultComponent(
        componentMetadata = componentMetadata,
        renderer = renderingStrategy),
    Disablable by Disablable.create(){

    private val valuePerStep: Double = range.toDouble() / numberOfSteps.toDouble()

    final override val currentValueProperty = createPropertyFrom(0)
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
        val actualValue = min(range, newValue)
        val actualStep = actualValue.toDouble() / valuePerStep
        val roundedStep = truncate(actualStep)
        currentStep = roundedStep.toInt()
    }

    override fun incrementCurrentValue() {
        if (currentValue < range) {
            currentValue++
        }
    }

    override fun decrementCurrentValue() {
        if (currentValue > 0) {
            currentValue--
        }
    }

    fun incrementCurrentStep() {
        setValueToClosestOfStep(currentStep + 1)
    }

    fun decrementCurrentStep() {
        setValueToClosestOfStep(currentStep - 1)
    }

    private fun addToCurrentValue(value: Int) {
        if (currentValue + value <= range) {
            currentValue += value
        } else {
            currentValue = range
        }
    }

    private fun subtractToCurrentValue(value: Int) {
        if (currentValue - value > 0) {
            currentValue -= value
        } else {
            currentValue = 0
        }
    }

    private fun setValueToClosestOfStep(step: Int) {
        var actualStep = step
        if (step < 0) {
            actualStep = 0
        }
        val calculatedValue = actualStep * valuePerStep
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

    override fun keyPressed(event: KeyboardEvent, phase: UIEventPhase) = whenEnabledRespondWith {
        if (phase == UIEventPhase.TARGET) {
            when (event.code) {
                KeyCode.RIGHT -> {
                    incrementCurrentValue()
                    Processed
                }
                KeyCode.LEFT -> {
                    decrementCurrentValue()
                    Processed
                }
                KeyCode.UP -> {
                    addToCurrentValue(valuePerStep.roundToInt())
                    Processed
                }
                KeyCode.DOWN -> {
                    subtractToCurrentValue(valuePerStep.roundToInt())
                    Processed
                }
                else -> Pass
            }
        } else Pass
    }

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

    override fun applyColorTheme(colorTheme: ColorTheme): ComponentStyleSet {
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
        LOGGER.debug("Slider (id=${id.abbreviate()}, disabled=$isDisabled, visibility=$isVisible) was rendered.")
        renderingStrategy.render(this, graphics)
    }

    override fun onValueChange(fn: ChangeListener<Int>): Subscription {
        return currentValueProperty.onChange(fn::onChange)
    }

    override fun onStepChange(fn: ChangeListener<Int>): Subscription {
        return currentStepProperty.onChange(fn::onChange)
    }

    companion object {
        val LOGGER = LoggerFactory.getLogger(Slider::class)
    }
}