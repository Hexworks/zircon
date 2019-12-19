package org.hexworks.zircon.internal.component.impl

import org.hexworks.zircon.api.behavior.Selectable
import org.hexworks.zircon.api.behavior.TextHolder
import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.RadioButton
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.extensions.whenEnabledRespondWith
import org.hexworks.zircon.api.uievent.MouseEvent
import org.hexworks.zircon.api.uievent.Pass
import org.hexworks.zircon.api.uievent.Processed
import org.hexworks.zircon.api.uievent.UIEventPhase
import org.hexworks.zircon.internal.component.impl.DefaultRadioButton.RadioButtonState.*

@Suppress("DuplicatedCode")
class DefaultRadioButton(componentMetadata: ComponentMetadata,
                         initialText: String,
                         private val renderingStrategy: ComponentRenderingStrategy<DefaultRadioButton>)
    : RadioButton,
        TextHolder by TextHolder.create(initialText),
        Selectable by Selectable.create(),
        DefaultComponent(
                componentMetadata = componentMetadata,
                renderer = renderingStrategy) {

    override val radioButtonState: RadioButtonState
        get() = currentState

    private var currentState = NOT_SELECTED

    init {
        render()
        textProperty.onChange {
            render()
        }
        selectedProperty.onChange { (_, _, newValue) ->
            currentState = if (newValue) {
                componentStyleSet.applyMouseOverStyle()
                SELECTED
            } else {
                componentStyleSet.reset()
                NOT_SELECTED
            }
            render()
        }
    }

    override fun mouseExited(event: MouseEvent, phase: UIEventPhase) = whenEnabledRespondWith {
        if (phase == UIEventPhase.TARGET) {
            currentState = if (selectedProperty.value) SELECTED else NOT_SELECTED
            componentStyleSet.reset()
            render()
            Processed
        } else Pass
    }

    override fun mousePressed(event: MouseEvent, phase: UIEventPhase) = whenEnabledRespondWith {
        if (phase == UIEventPhase.TARGET) {
            currentState = PRESSED
            componentStyleSet.applyActiveStyle()
            render()
            Processed
        } else Pass
    }

    override fun activated() = whenEnabledRespondWith {
        isSelected = true
        Processed
    }

    override fun convertColorTheme(colorTheme: ColorTheme) = ComponentStyleSetBuilder.newBuilder()
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
            .build()

    enum class RadioButtonState {
        PRESSED,
        SELECTED,
        NOT_SELECTED
    }
}
