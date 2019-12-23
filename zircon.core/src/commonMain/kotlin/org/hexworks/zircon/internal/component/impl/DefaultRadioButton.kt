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
import org.hexworks.zircon.api.uievent.Processed
import org.hexworks.zircon.api.uievent.UIEventPhase
import org.hexworks.zircon.api.uievent.UIEventResponse
import org.hexworks.zircon.internal.component.impl.DefaultRadioButton.RadioButtonState.NOT_SELECTED
import org.hexworks.zircon.internal.component.impl.DefaultRadioButton.RadioButtonState.PRESSED
import org.hexworks.zircon.internal.component.impl.DefaultRadioButton.RadioButtonState.SELECTED

@Suppress("DuplicatedCode")
class DefaultRadioButton(componentMetadata: ComponentMetadata,
                         initialText: String,
                         override val key: String,
                         renderingStrategy: ComponentRenderingStrategy<DefaultRadioButton>)
    : RadioButton,
        TextHolder by TextHolder.create(initialText),
        Selectable by Selectable.create(),
        DefaultComponent(
                componentMetadata = componentMetadata,
                renderer = renderingStrategy) {

    override var state = NOT_SELECTED

    init {
        render()
        textProperty.onChange {
            render()
        }
        selectedProperty.onChange { (_, _, newValue) ->
            state = if (newValue) {
                SELECTED
            } else {
                NOT_SELECTED
            }
            render()
        }
    }

    override fun mouseExited(event: MouseEvent, phase: UIEventPhase) = whenEnabledRespondWith {
        if (phase == UIEventPhase.TARGET) {
            state = if (isSelected) SELECTED else NOT_SELECTED
        }
        super.mouseExited(event, phase)
    }

    override fun mousePressed(event: MouseEvent, phase: UIEventPhase) = whenEnabledRespondWith {
        if (phase == UIEventPhase.TARGET) {
            state = PRESSED
        }
        super.mousePressed(event, phase)
    }

    override fun mouseReleased(event: MouseEvent, phase: UIEventPhase): UIEventResponse {
        if (phase == UIEventPhase.TARGET) {
            state = SELECTED
            isSelected = true
        }
        return super.mouseReleased(event, phase)
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
