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
import org.hexworks.zircon.api.component.ScrollBar
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.extensions.abbreviate
import org.hexworks.zircon.api.extensions.whenEnabled
import org.hexworks.zircon.api.extensions.whenEnabledRespondWith
import org.hexworks.zircon.api.uievent.*
import kotlin.math.ceil
import kotlin.math.roundToInt
import kotlin.math.truncate

abstract class BaseScrollBar(final override val minValue: Int,
                             final override var maxValue: Int,
                             final override val numberOfSteps: Int,
                             final override var itemsShownAtOnce: Int,
                             componentMetadata: ComponentMetadata,
                             private val renderingStrategy: ComponentRenderingStrategy<ScrollBar>) :
    ScrollBar, DefaultComponent(
        componentMetadata = componentMetadata,
        renderer = renderingStrategy),
        Disablable by Disablable.create(){

    private var range: Int = maxValue - minValue
    protected var valuePerStep: Double = range.toDouble() / numberOfSteps.toDouble()

    final override val currentValueProperty = createPropertyFrom(minValue)
    final override var currentValue: Int by currentValueProperty.asDelegate()

    final override val currentStepProperty = createPropertyFrom(minValue)
    final override var currentStep: Int by currentStepProperty.asDelegate()

    override var barSizeInSteps = (itemsShownAtOnce/valuePerStep).roundToInt()

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
                LOGGER.debug("Disabling ScrollBar (id=${id.abbreviate()}, disabled=$isDisabled).")
                componentStyleSet.applyDisabledStyle()
            } else {
                LOGGER.debug("Enabling ScrollBar (id=${id.abbreviate()}, disabled=$isDisabled).")
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
        }
    }

    override fun decrementStep() {
        if (currentStep - 1 >= 0) {
            computeValueToClosestOfStep(currentStep - 1)
        }
    }

    override fun resizeScrollBar(newMaxValue: Int) {
        if (newMaxValue >= 0) {
            maxValue = newMaxValue
            range = maxValue - minValue
            valuePerStep = range.toDouble() / numberOfSteps.toDouble()

            barSizeInSteps = ceil(itemsShownAtOnce/valuePerStep).toInt()
            addToCurrentValues(0)
            render()
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

    private fun computeClickedZone(clickPosition: Int) : ClickedZone {
        return when {
            clickPosition < currentStep -> Companion.ClickedZone.TowardsLow
            clickPosition > currentStep + barSizeInSteps -> Companion.ClickedZone.TowardsHigh
            else -> Companion.ClickedZone.InsideBar
        }
    }

    abstract fun getMousePosition(event: MouseEvent): Int

    override fun acceptsFocus() = isDisabled.not()

    override fun focusGiven() = whenEnabled {
        LOGGER.debug("ScrollBar (id=${id.abbreviate()}, disabled=$isDisabled) was given focus.")
        componentStyleSet.applyFocusedStyle()
        render()
    }

    override fun focusTaken() = whenEnabled {
        LOGGER.debug("ScrollBar (id=${id.abbreviate()}, disabled=$isDisabled) lost focus.")
        componentStyleSet.reset()
        render()
    }

    override fun mouseEntered(event: MouseEvent, phase: UIEventPhase) = whenEnabledRespondWith {
        if (phase == UIEventPhase.TARGET) {
            LOGGER.debug("ScrollBar (id=${id.abbreviate()}, disabled=$isDisabled) was mouse entered.")
            componentStyleSet.applyMouseOverStyle()
            render()
            Processed
        } else Pass
    }

    override fun mouseExited(event: MouseEvent, phase: UIEventPhase) = whenEnabledRespondWith {
        if (phase == UIEventPhase.TARGET) {
            LOGGER.debug("ScrollBar (id=${id.abbreviate()}, disabled=$isDisabled) was mouse exited.")
            componentStyleSet.reset()
            render()
            Processed
        } else Pass
    }

    override fun mousePressed(event: MouseEvent, phase: UIEventPhase) = whenEnabledRespondWith {
        if (phase == UIEventPhase.TARGET) {
            LOGGER.debug("ScrollBar (id=${id.abbreviate()}, disabled=$isDisabled) was mouse pressed.")
            componentStyleSet.applyActiveStyle()

            when (computeClickedZone(getMousePosition(event))) {
                Companion.ClickedZone.TowardsLow -> decrementStep()
                Companion.ClickedZone.TowardsHigh -> incrementStep()
                else -> {}
            }

            render()
            Processed
        } else Pass
    }

    override fun mouseReleased(event: MouseEvent, phase: UIEventPhase) = whenEnabledRespondWith {
        if (phase == UIEventPhase.TARGET) {
            LOGGER.debug("ScrollBar (id=${id.abbreviate()}, disabled=$isDisabled) was mouse released.")
            componentStyleSet.applyMouseOverStyle()
            render()
            Processed
        } else Pass
    }

    override fun mouseDragged(event: MouseEvent, phase: UIEventPhase) = whenEnabledRespondWith {
        if (phase == UIEventPhase.TARGET) {
            LOGGER.debug("ScrollBar (id=${id.abbreviate()}, disabled=$isDisabled) was mouse pressed.")
            componentStyleSet.applyActiveStyle()

            val mousePosition = getMousePosition(event)
            computeValueToClosestOfStep(mousePosition - (barSizeInSteps/2))

            render()
            Processed
        } else Pass
    }

    abstract override fun keyPressed(event: KeyboardEvent, phase: UIEventPhase): UIEventResponse

    override fun activated() = whenEnabledRespondWith {
        if (isDisabled) {
            LOGGER.warn("Trying to activate disabled ScrollBar (id=${id.abbreviate()}. Request dropped.")
            Pass
        } else {
            LOGGER.debug("ScrollBar (id=${id.abbreviate()}, disabled=$isDisabled) was activated.")
            componentStyleSet.applyMouseOverStyle()
            render()
            Processed
        }
    }

    override fun applyColorTheme(colorTheme: ColorTheme): ComponentStyleSet {
        LOGGER.debug("Applying color theme: $colorTheme to ScrollBar (id=${id.abbreviate()}, disabled=$isDisabled).")
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
        LOGGER.debug("ScrollBar (id=${id.abbreviate()}, disabled=$isDisabled, visibility=$isVisible) was rendered.")
        renderingStrategy.render(this, graphics)
    }

    override fun onValueChange(fn: ChangeListener<Int>): Subscription {
        return currentValueProperty.onChange(fn::onChange)
    }

    override fun onStepChange(fn: ChangeListener<Int>): Subscription {
        return currentStepProperty.onChange(fn::onChange)
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