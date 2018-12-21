package org.hexworks.zircon.internal.component.impl

import org.hexworks.cobalt.databinding.api.createPropertyFrom
import org.hexworks.cobalt.databinding.api.extensions.onChange
import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.cobalt.datatypes.sam.Consumer
import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.RadioButton
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.input.Input
import org.hexworks.zircon.api.input.MouseAction
import org.hexworks.zircon.api.util.Runnable
import org.hexworks.zircon.internal.behavior.Observable
import org.hexworks.zircon.internal.behavior.impl.DefaultObservable
import org.hexworks.zircon.internal.component.impl.DefaultRadioButton.RadioButtonState.*

class DefaultRadioButton(componentMetadata: ComponentMetadata,
                         initialText: String,
                         private val renderingStrategy: ComponentRenderingStrategy<DefaultRadioButton>)
    : RadioButton,
        Observable<Unit> by DefaultObservable(),
        DefaultComponent(
                componentMetadata = componentMetadata,
                renderer = renderingStrategy) {

    override val textProperty = createPropertyFrom(initialText).also {
        it.onChange {
            render()
        }
    }

    override val text: String
        get() = textProperty.value

    override val state: RadioButtonState
        get() = currentState

    override val selectedValue = createPropertyFrom(false).also { prop ->
        prop.onChange {
            render()
        }
    }

    private var currentState = NOT_SELECTED

    init {
        render()
    }

    override fun mouseExited(action: MouseAction) {
        currentState = if (selectedValue.value) SELECTED else NOT_SELECTED
        componentStyleSet.reset()
        render()
    }

    override fun mousePressed(action: MouseAction) {
        currentState = PRESSED
        componentStyleSet.applyActiveStyle()
        render()
    }

    override fun mouseReleased(action: MouseAction) {
        select()
    }

    override fun isSelected() = selectedValue.value

    override fun onSelected(runnable: Runnable): Subscription {
        return addObserver(object : Consumer<Unit> {
            override fun accept(value: Unit) {
                runnable.run()
            }
        })
    }

    override fun acceptsFocus(): Boolean {
        return true
    }

    override fun giveFocus(input: Maybe<Input>): Boolean {
        componentStyleSet.applyFocusedStyle()
        render()
        return true
    }

    override fun takeFocus(input: Maybe<Input>) {
        componentStyleSet.reset()
        render()
    }

    override fun applyColorTheme(colorTheme: ColorTheme): ComponentStyleSet {
        return ComponentStyleSetBuilder.newBuilder()
                .withDefaultStyle(StyleSetBuilder.newBuilder()
                        .withForegroundColor(colorTheme.accentColor)
                        .withBackgroundColor(TileColor.transparent())
                        .build())
                .withMouseOverStyle(StyleSetBuilder.newBuilder()
                        .withForegroundColor(colorTheme.primaryBackgroundColor)
                        .withBackgroundColor(colorTheme.accentColor)
                        .build())
                .withFocusedStyle(StyleSetBuilder.newBuilder()
                        .withForegroundColor(colorTheme.secondaryBackgroundColor)
                        .withBackgroundColor(colorTheme.accentColor)
                        .build())
                .withActiveStyle(StyleSetBuilder.newBuilder()
                        .withForegroundColor(colorTheme.secondaryForegroundColor)
                        .withBackgroundColor(colorTheme.accentColor)
                        .build())
                .build().also {
                    componentStyleSet = it
                    render()
                }
    }

    override fun render() {
        renderingStrategy.render(this, graphics)
    }

    override fun select() {
        componentStyleSet.applyMouseOverStyle()
        currentState = SELECTED
        selectedValue.value = true
        notifyObservers(Unit)
    }

    fun removeSelection() {
        componentStyleSet.reset()
        currentState = NOT_SELECTED
        selectedValue.value = false
    }

    enum class RadioButtonState {
        PRESSED,
        SELECTED,
        NOT_SELECTED
    }
}
