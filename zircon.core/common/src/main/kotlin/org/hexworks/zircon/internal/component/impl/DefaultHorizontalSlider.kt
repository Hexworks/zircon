package org.hexworks.zircon.internal.component.impl

import org.hexworks.cobalt.databinding.api.createPropertyFrom
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.Sizes
import org.hexworks.zircon.api.behavior.ChangeListener
import org.hexworks.zircon.api.behavior.Disablable
import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.component.*
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.extensions.*
import org.hexworks.zircon.api.graphics.Symbols
import org.hexworks.zircon.api.uievent.*
import org.hexworks.zircon.internal.component.renderer.DefaultSliderGutterRenderer
import kotlin.math.min
import kotlin.math.truncate

class DefaultHorizontalSlider(componentMetadata: ComponentMetadata,
                              private val renderingStrategy: ComponentRenderingStrategy<Slider>,
                              override val range: Int,
                              override val numberOfSteps: Int,
                              val isDecorated: Boolean
) : Slider, DefaultContainer(
        componentMetadata = componentMetadata,
        renderer = renderingStrategy),
        Disablable by Disablable.create() {


    override val currentValueProperty = createPropertyFrom(0)
    override var currentValue: Int by currentValueProperty.asDelegate()

    private val valuePerStep: Double = range.toDouble() / numberOfSteps.toDouble()
    private val actualSize: Size = when(isDecorated) {
        true -> Sizes.create(size.width - 2, size.height - 2)
        false -> size
    }
    private val labelSize: Size = Sizes.create(range.toString().length, 1)

    private val root = Components.hbox()
            .withSize(actualSize)
            .withDecorations()
            .withSpacing(0)
            .build()

    private val decrementButton = Components.button()
            .withSize(Size.one())
            .withDecorations()
            .withText("${Symbols.TRIANGLE_LEFT_POINTING_BLACK}")
            .build().apply {
                handleComponentEvents(ComponentEventType.ACTIVATED) {
                    decrementCurrentValue()
                    Processed
                }
            }

    private val incrementButton = Components.button()
            .withSize(Size.one())
            .withDecorations()
            .withText("${Symbols.TRIANGLE_RIGHT_POINTING_BLACK}")
            .build().apply {
                handleComponentEvents(ComponentEventType.ACTIVATED) {
                    incrementCurrentValue()
                    Processed
                }
            }

    private val gutterPanel = Components.panel()
            .withSize(numberOfSteps + 1, 1)
            .withDecorations()
            .withComponentRenderer(DefaultSliderGutterRenderer(::getCurrentValueState))
            .build().apply {
                processMouseEvents(MouseEventType.MOUSE_PRESSED) { event, _ ->
                    val clickPosition = getMousePosition(event)
                    setValueToClosestPossible(clickPosition)
                    render()
                }

                processMouseEvents(MouseEventType.MOUSE_DRAGGED) { event, _ ->
                    val dragPosition = getMousePosition(event)
                    setValueToClosestPossible(dragPosition)
                    render()
                }
            }

    private val valueLabel = Components.label()
            .withText("$currentValue")
            .withDecorations()
            .withSize(labelSize)
            //TODO: Better be aligned around root or this, but the boundable check throws an error
            .withAlignmentAround(incrementButton, ComponentAlignment.RIGHT_CENTER)
            .build()

    init {
        root.apply {
            addComponent(decrementButton)
            addComponent(gutterPanel)
            addComponent(incrementButton)
            addComponent(valueLabel)
        }
        addComponent(root)
        render()

        currentValueProperty.onChange {
            valueLabel.text = "${it.newValue}"
            gutterPanel.requestFocus()
            println("UPDATING VALUE FOR REAL")
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

    override fun applyColorTheme(colorTheme: ColorTheme): ComponentStyleSet {
        LOGGER.debug("Applying color theme ($colorTheme) to Slider (id=${id.abbreviate()}).")
        return ComponentStyleSetBuilder.newBuilder()
                .withDefaultStyle(StyleSetBuilder.newBuilder()
                        .withForegroundColor(colorTheme.secondaryForegroundColor)
                        .withBackgroundColor(TileColor.transparent())
                        .build())
                .withDisabledStyle(StyleSetBuilder.newBuilder()
                        .withForegroundColor(colorTheme.secondaryForegroundColor)
                        .withBackgroundColor(colorTheme.secondaryBackgroundColor)
                        .build())
                .build().also {
                    componentStyleSet = it
                    render()
                    children.forEach {child ->
                        child.applyColorTheme(colorTheme)
                    }
                    gutterPanel.componentStyleSet = it
                }
    }

    fun getCurrentValueState(): CurrentValueState {
        println("COMPUTING NEW STATE")
        val actualValue = min(range, currentValue)
        println("actualValue: $actualValue")
        val ratio = actualValue.toDouble() / range.toDouble()
        println("ratio: $ratio")
        val currentStep = (truncate(ratio * numberOfSteps)).toInt()
        println("currentStep: $currentStep")
        return CurrentValueState(currentStep, actualValue, range)
    }

    private fun getMousePosition(event: MouseEvent): Int {
        val clickPosition = event.position.minus(this.absolutePosition).x
        return when(isDecorated) {
            true -> clickPosition - 1
            false -> clickPosition
        }
    }

    private fun incrementCurrentValue() {
        if (currentValue < range) {
            currentValue++
            render()
        }
    }

    private fun decrementCurrentValue() {
        if (currentValue > 0) {
            currentValue--
            render()
        }
    }

    private fun setValueToClosestPossible(value: Int) {
        currentValue = (truncate((value - 1) * valuePerStep)).toInt()
    }

    override fun onChange(fn: ChangeListener<Int>): Subscription {
        return currentValueProperty.onChange(fn::onChange)
    }

    override fun render() {
        LOGGER.debug("Slider (id=${id.abbreviate()}, visibility=$isVisible) was rendered.")
        renderingStrategy.render(this, graphics)
    }

    companion object {
        val LOGGER = LoggerFactory.getLogger(Slider::class)
    }

    data class CurrentValueState(val width: Int, val actualValue: Int, val maxValue: Int)
}