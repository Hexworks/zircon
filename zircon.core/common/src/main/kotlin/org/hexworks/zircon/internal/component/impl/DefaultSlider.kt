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
import org.hexworks.zircon.api.extensions.processMouseEvents
import org.hexworks.zircon.api.uievent.ComponentEventType
import org.hexworks.zircon.api.uievent.MouseEvent
import org.hexworks.zircon.api.uievent.MouseEventType
import org.hexworks.zircon.api.uievent.Processed
import org.hexworks.zircon.internal.component.InternalComponent
import kotlin.math.min
import kotlin.math.truncate

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
    abstract val labelSize: Size

    abstract val actualSize: Size
    abstract val decrementButtonChar: Char
    abstract val incrementButtonChar: Char

    abstract val root: Container
    abstract val labelRenderer: ComponentRenderer<DefaultLabel>
    abstract val gutterRenderer: ComponentRenderer<Panel>
    abstract val gutterPanelSize: Size

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

    private lateinit var gutterPanel: Panel
    private lateinit var valueLabel: Label

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
                    root.componentStyleSet = it
                    root.children.forEach {component ->
                        component.componentStyleSet = it
                    }
                    render()
                }
    }

    fun getCurrentValueState(): CurrentValueState {
        //TODO: Fix weird discrepencies with rounding/truncate
        val actualValue = min(range, currentValue)
        println("actualValue: $actualValue")
        val ratio = actualValue.toDouble() / range.toDouble()
        println("ratio: $ratio")
        val currentStep = (truncate(ratio * numberOfSteps)).toInt()
        println("currentStep: $currentStep")
        return CurrentValueState(currentStep, actualValue, range)
    }

    abstract fun getMousePosition(event: MouseEvent): Int

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
        currentValue = (truncate((value - 1) * valuePerStep)).toInt()
    }

    protected fun finishInit() {
        gutterPanel = Components.panel()
                .withSize(gutterPanelSize)
                .withDecorations()
                .withComponentRenderer(gutterRenderer)
                .build().apply {
                    processMouseEvents(MouseEventType.MOUSE_PRESSED) { event, _ ->
                        setValueToClosestPossible(getMousePosition(event))
                        render()
                    }

                    processMouseEvents(MouseEventType.MOUSE_DRAGGED) { event, _ ->
                        setValueToClosestPossible(getMousePosition(event))
                        render()
                    }
                }

        valueLabel =  Components.label()
                .withText("")
                .withComponentRenderer(labelRenderer as ComponentRenderer<Label>)
                .withDecorations()
                .withSize(labelSize)
                .build()

        root.apply {
            addComponent(decrementButton)
            addComponent(gutterPanel)
            addComponent(incrementButton)
            addComponent(valueLabel)
        }
        addComponent(root)

        currentValueProperty.onChange {
            valueLabel.text = "${it.newValue}"
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
        (root as? InternalComponent)?.render()
        root.children.forEach {component ->
            (component as? InternalComponent)?.render()
        }
    }

    companion object {
        val LOGGER = LoggerFactory.getLogger(Slider::class)
    }

    data class CurrentValueState(val steps: Int, val actualValue: Int, val maxValue: Int)
}