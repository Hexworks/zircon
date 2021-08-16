package org.hexworks.zircon.internal.component.impl

import org.hexworks.zircon.api.behavior.Selectable
import org.hexworks.zircon.api.behavior.TextOverride
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.RadioButton
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.extensions.whenEnabledRespondWith
import org.hexworks.zircon.api.uievent.MouseEvent
import org.hexworks.zircon.api.uievent.UIEventPhase
import org.hexworks.zircon.internal.component.impl.DefaultRadioButton.RadioButtonState.NOT_SELECTED
import org.hexworks.zircon.internal.component.impl.DefaultRadioButton.RadioButtonState.PRESSED
import org.hexworks.zircon.internal.component.impl.DefaultRadioButton.RadioButtonState.SELECTED

class DefaultRadioButton internal constructor(
    componentMetadata: ComponentMetadata,
    initialText: String,
    override val key: String,
    renderingStrategy: ComponentRenderingStrategy<RadioButton>
) : RadioButton,
    TextOverride by TextOverride.create(initialText),
    Selectable by Selectable.create(),
    DefaultComponent(
        metadata = componentMetadata,
        renderer = renderingStrategy
    ) {

    override var state = NOT_SELECTED

    init {
        selectedProperty.onChange { (_, newValue) ->
            state = if (newValue) {
                SELECTED
            } else {
                NOT_SELECTED
            }
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

    override fun mouseReleased(event: MouseEvent, phase: UIEventPhase) = whenEnabledRespondWith {
        if (phase == UIEventPhase.TARGET) {
            state = SELECTED
            isSelected = true
        }
        super.mouseReleased(event, phase)
    }

    override fun activated() = whenEnabledRespondWith {
        isSelected = true
        super.activated()
    }

    override fun convertColorTheme(colorTheme: ColorTheme) = colorTheme.toInteractiveStyle()

    enum class RadioButtonState {
        PRESSED,
        SELECTED,
        NOT_SELECTED
    }
}
