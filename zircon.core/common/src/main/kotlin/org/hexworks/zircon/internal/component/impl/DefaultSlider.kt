package org.hexworks.zircon.internal.component.impl

import org.hexworks.cobalt.databinding.api.createPropertyFrom
import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.behavior.ChangeListener
import org.hexworks.zircon.api.behavior.Disablable
import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.component.*
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.extensions.abbreviate
import org.hexworks.zircon.api.extensions.handleComponentEvents
import org.hexworks.zircon.api.extensions.onValueChanged
import org.hexworks.zircon.api.extensions.processMouseEvents
import org.hexworks.zircon.api.uievent.ComponentEventType
import org.hexworks.zircon.api.uievent.MouseEvent
import org.hexworks.zircon.api.uievent.MouseEventType
import org.hexworks.zircon.api.uievent.Processed
import org.hexworks.zircon.internal.component.SliderGutter
import kotlin.math.min
import kotlin.math.truncate
import kotlin.math.roundToInt

@Suppress("UNCHECKED_CAST")
abstract class DefaultSlider(componentMetadata: ComponentMetadata,
                            private val renderingStrategy: ComponentRenderingStrategy<Slider>,
                            final override val range: Int,
                            final override val numberOfSteps: Int,
                            val isDecorated: Boolean
) : Slider, DefaultContainer(
        componentMetadata = componentMetadata,
        renderer = renderingStrategy),
        Disablable by Disablable.create() {

    final override val currentValueProperty = createPropertyFrom(0)
    override var currentValue: Int by currentValueProperty.asDelegate()
    private val valuePerStep: Double = range.toDouble() / numberOfSteps.toDouble()

    abstract val actualSize: Size
    abstract val labelSize: Size
    abstract val decrementButtonChar: Char
    abstract val incrementButtonChar: Char

    abstract val root: Container
    abstract val numberInputRenderer: ComponentRenderer<DefaultNumberInput>
    abstract val gutter: SliderGutter

    private lateinit var valueInput: NumberInput

    private val decrementButton = Components.button()
            .withSize(Size.one())
            .withDecorations()
            .withText("")
            .build().apply {
                handleComponentEvents(ComponentEventType.ACTIVATED) {
                    decrementCurrentValue()
                    Processed
                }
            }

    private val incrementButton = Components.button()
            .withSize(Size.one())
            .withDecorations()
            .withText("")
            .build().apply {
                handleComponentEvents(ComponentEventType.ACTIVATED) {
                    incrementCurrentValue()
                    Processed
                }
            }

    abstract fun getMousePosition(event: MouseEvent): Int

    private fun computeCurrentStep(newValue: Int): Int {
        val actualValue = min(range, newValue)
        val actualStep = actualValue.toDouble() / valuePerStep
        val roundedStep = truncate(actualStep)
        return roundedStep.toInt()
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

    private fun setValueToClosestPossible(step: Int) {
        val trueStep = step - 1
        val calculatedValue = trueStep * valuePerStep
        currentValue = calculatedValue.roundToInt()
    }

    override fun applyColorTheme(colorTheme: ColorTheme): ComponentStyleSet {
        LOGGER.debug("Applying color theme ($colorTheme) to Slider (id=${id.abbreviate()}).")
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
                    root.applyColorTheme(colorTheme)
                    root.children.forEach {component ->
                        component.applyColorTheme(colorTheme)
                    }
                    render()
                }
    }

    protected fun finishInit() {
        gutter.apply {
                    processMouseEvents(MouseEventType.MOUSE_PRESSED) { event, _ ->
                        setValueToClosestPossible(getMousePosition(event))
                        render()
                    }

                    processMouseEvents(MouseEventType.MOUSE_DRAGGED) { event, _ ->
                        setValueToClosestPossible(getMousePosition(event))
                        render()
                    }
                }

        valueInput =  Components.numberInput(range.toString().length)
                .withInitialValue(currentValue)
                .withMaxValue(range)
                .withComponentRenderer(numberInputRenderer as ComponentRenderer<NumberInput>)
                .withDecorations()
                .withSize(labelSize)
                .build().apply {
                    onValueChanged {
                        if (this@DefaultSlider.currentValue != it.newValue) {
                            println("this: $this, value: ${it.newValue}")
                            this@DefaultSlider.currentValue = it.newValue
                        }
                    }
                }

        root.apply {
            addComponent(decrementButton)
            addComponent(gutter)
            addComponent(incrementButton)
            addComponent(valueInput)
        }
        addComponent(root)

        currentValueProperty.onChange {
            valueInput.text = "${it.newValue}"
            gutter.currentValue = computeCurrentStep(it.newValue)
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

        incrementButton.text = "$incrementButtonChar"
        decrementButton.text = "$decrementButtonChar"
        render()
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
}