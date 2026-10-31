package org.hexworks.zircon.internal.component.impl

import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.RadioButton.RadioButtonState
import org.hexworks.zircon.api.uievent.UIEventResponse
import org.hexworks.zircon.api.behavior.Selectable
import org.hexworks.zircon.api.behavior.TextOverride
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.RadioButton
import org.hexworks.zircon.api.component.RadioButton.RadioButtonState.NOT_SELECTED
import org.hexworks.zircon.api.component.RadioButton.RadioButtonState.PRESSED
import org.hexworks.zircon.api.component.RadioButton.RadioButtonState.SELECTED
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.extensions.whenEnabledRespondWith
import org.hexworks.zircon.api.uievent.MouseEvent
import org.hexworks.zircon.api.uievent.UIEventPhase

public class DefaultRadioButton internal constructor(
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
    override var state: RadioButtonState = NOT_SELECTED

    /** `true` between the activation (press) and the deactivation (release). */
    private var pressing = false

    /** `true` while [pressing] and the cursor is outside the button: a release there cancels. */
    private var cursorOutside = false

    init {
        selectedProperty.onChange { (_, newValue) ->
            state = if (newValue) {
                SELECTED
            } else {
                NOT_SELECTED
            }
        }
    }

    // <o> stays for the whole press, even when the cursor leaves the button (the dispatcher
    // keeps delivering the press to it); only the colours follow the hover.
    override fun mouseExited(event: MouseEvent, phase: UIEventPhase): UIEventResponse = whenEnabledRespondWith {
        if (phase == UIEventPhase.TARGET && pressing) cursorOutside = true
        super.mouseExited(event, phase)
    }

    override fun mouseEntered(event: MouseEvent, phase: UIEventPhase): UIEventResponse = whenEnabledRespondWith {
        if (phase == UIEventPhase.TARGET && pressing) cursorOutside = false
        super.mouseEntered(event, phase)
    }

    override fun activated(): UIEventResponse = whenEnabledRespondWith {
        pressing = true
        cursorOutside = false
        state = PRESSED
        super.activated()
    }

    override fun deactivated(): UIEventResponse = whenEnabledRespondWith {
        // Released outside the button: the press is cancelled, like a button in a browser.
        if (pressing && !cursorOutside) {
            isSelected = true
        }
        pressing = false
        state = if (isSelected) SELECTED else NOT_SELECTED
        super.deactivated()
    }

    override fun focusTaken(): UIEventResponse = whenEnabledRespondWith {
        pressing = false
        state = if (isSelected) SELECTED else NOT_SELECTED
        super.focusTaken()
    }

    override fun convertColorTheme(colorTheme: ColorTheme): ComponentStyleSet = colorTheme.toInteractiveStyle()

}
