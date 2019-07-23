package org.hexworks.zircon.internal.component.impl

import org.hexworks.cobalt.databinding.api.createPropertyFrom
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.behavior.ChangeListener
import org.hexworks.zircon.api.behavior.Disablable
import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.component.*
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.extensions.abbreviate
import org.hexworks.zircon.api.extensions.whenEnabled
import org.hexworks.zircon.api.extensions.whenEnabledRespondWith
import org.hexworks.zircon.api.uievent.*
import kotlin.math.min
import kotlin.math.round
import kotlin.math.truncate

class DefaultVerticalSlider(componentMetadata: ComponentMetadata,
                            private val renderingStrategy: ComponentRenderingStrategy<VerticalSlider>,
                            override val range: Int,
                            override val numberOfSteps: Int,
                            val additionalHeightNeeded: Int,
                            val shouldOffsetMouse: Boolean
) : VerticalSlider, DefaultComponent(
        componentMetadata = componentMetadata,
        renderer = renderingStrategy),
        Disablable by Disablable.create() {


    override val currentValueProperty = createPropertyFrom(0)
    override var currentValue: Int by currentValueProperty.asDelegate()

    private val valuePerStep: Double = range.toDouble() / numberOfSteps.toDouble()

    init {
        render()
        currentValueProperty.onChange {
            render()
        }
        disabledProperty.onChange {
            if (it.newValue) {
                LOGGER.debug("Disabling VerticalSlider (id=${id.abbreviate()}, disabled=$isDisabled).")
                componentStyleSet.applyDisabledStyle()
            } else {
                LOGGER.debug("Enabling VerticalSlider (id=${id.abbreviate()}, disabled=$isDisabled).")
                componentStyleSet.reset()
            }
            render()
        }
    }

    override fun acceptsFocus() = isDisabled.not()

    override fun focusGiven() = whenEnabled {
        LOGGER.debug("VerticalSlider (id=${id.abbreviate()}, disabled=$isDisabled) was given focus.")
        componentStyleSet.applyFocusedStyle()
        render()
    }

    override fun focusTaken() = whenEnabled {
        LOGGER.debug("VerticalSlider (id=${id.abbreviate()}, disabled=$isDisabled) lost focus.")
        componentStyleSet.reset()
        render()
    }

    override fun mouseEntered(event: MouseEvent, phase: UIEventPhase) = whenEnabledRespondWith {
        if (phase == UIEventPhase.TARGET) {
            LOGGER.debug("VerticalSlider (id=${id.abbreviate()}, disabled=$isDisabled) was mouse entered.")
            componentStyleSet.applyMouseOverStyle()
            render()
            Processed
        } else Pass
    }

    override fun mouseExited(event: MouseEvent, phase: UIEventPhase) = whenEnabledRespondWith {
        if (phase == UIEventPhase.TARGET) {
            LOGGER.debug("VerticalSlider (id=${id.abbreviate()}, disabled=$isDisabled) was mouse exited.")
            componentStyleSet.reset()
            render()
            Processed
        } else Pass
    }

    override fun mousePressed(event: MouseEvent, phase: UIEventPhase) = whenEnabledRespondWith {
        if (phase == UIEventPhase.TARGET) {
            LOGGER.debug("VerticalSlider (id=${id.abbreviate()}, disabled=$isDisabled) was mouse pressed.")
            componentStyleSet.applyActiveStyle()
            when (val clickPosition = getMousePosition(event)) {
                0 -> {
                    decrementCurrentValue()
                }
                (numberOfSteps + 2) -> {
                    incrementCurrentValue()
                }
                else -> {setValueToClosestPossible(clickPosition)}
            }

            render()
            Processed
        } else Pass
    }

    override fun mouseReleased(event: MouseEvent, phase: UIEventPhase) = whenEnabledRespondWith {
        if (phase == UIEventPhase.TARGET) {
            LOGGER.debug("VerticalSlider (id=${id.abbreviate()}, disabled=$isDisabled) was mouse released.")
            componentStyleSet.applyMouseOverStyle()
            render()
            Processed
        } else Pass
    }

    override fun mouseDragged(event: MouseEvent, phase: UIEventPhase) = whenEnabledRespondWith {
        if (phase == UIEventPhase.TARGET) {
            LOGGER.debug("VerticalSlider (id=${id.abbreviate()}, disabled=$isDisabled) was mouse dragged.")

            when (val dragPosition = getMousePosition(event)) {
                0 -> {}
                (numberOfSteps + 2) -> {}
                else -> {setValueToClosestPossible(dragPosition)}
            }
            render()
            Processed
        } else Pass
    }

    override fun applyColorTheme(colorTheme: ColorTheme): ComponentStyleSet {
        LOGGER.debug("Applying color theme ($colorTheme) to VerticalSlider (id=${id.abbreviate()}).")
        return ComponentStyleSetBuilder.newBuilder()
                .withDefaultStyle(StyleSetBuilder.newBuilder()
                        .withForegroundColor(colorTheme.secondaryForegroundColor)
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

    fun getCurrentValueState(): CurrentValueState {
        val actualValue = min(range, currentValue)
        println("actualValue: $actualValue")
        val ratio = actualValue.toDouble() / range.toDouble()
        println("ratio: $ratio")
        val currentStep = (truncate(ratio * numberOfSteps)).toInt()
        println("currentStep: $currentStep")
        return CurrentValueState(currentStep, actualValue)
    }

    private fun getMousePosition(event: MouseEvent): Int {
        val clickPosition = event.position.minus(this.absolutePosition).y
        return when(shouldOffsetMouse) {
            true -> clickPosition - 1
            false -> clickPosition
        }
    }

    private fun incrementCurrentValue() {
        if (currentValue < range) {
            currentValue++
        }
    }

    private fun decrementCurrentValue() {
        if (currentValue > 0) {
            currentValue--
        }
    }

    private fun setValueToClosestPossible(value: Int) {
        currentValue = (round((value - 1) * valuePerStep)).toInt()
    }

    override fun onChange(fn: ChangeListener<Int>): Subscription {
        return currentValueProperty.onChange(fn::onChange)
    }

    override fun render() {
        LOGGER.debug("VerticalSlider (id=${id.abbreviate()}, visibility=$isVisible) was rendered.")
        renderingStrategy.render(this, graphics)
    }

    companion object {
        val LOGGER = LoggerFactory.getLogger(VerticalSlider::class)
    }

    data class CurrentValueState(val height: Int, val actualValue: Int)
}