package org.hexworks.zircon.internal.component.impl

import org.hexworks.zircon.api.component.CheckBox.CheckBoxState
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.uievent.UIEventResponse
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.api.behavior.Selectable
import org.hexworks.zircon.api.behavior.TextOverride
import org.hexworks.zircon.api.component.CheckBox
import org.hexworks.zircon.api.component.CheckBox.CheckBoxAlignment
import org.hexworks.zircon.api.component.CheckBox.CheckBoxAlignment.RIGHT
import org.hexworks.zircon.api.component.CheckBox.CheckBoxState.CHECKED
import org.hexworks.zircon.api.component.CheckBox.CheckBoxState.CHECKING
import org.hexworks.zircon.api.component.CheckBox.CheckBoxState.UNCHECKED
import org.hexworks.zircon.api.component.CheckBox.CheckBoxState.UNCHECKING
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.data.ComponentState
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.extensions.whenEnabled
import org.hexworks.zircon.api.extensions.whenEnabledRespondWith
import org.hexworks.zircon.api.uievent.MouseEvent
import org.hexworks.zircon.api.uievent.UIEventPhase

@Suppress("DuplicatedCode")
public class DefaultCheckBox internal constructor(
    componentMetadata: ComponentMetadata,
    textProperty: Property<String>,
    override val labelAlignment: CheckBoxAlignment = RIGHT,
    renderingStrategy: ComponentRenderingStrategy<CheckBox>
) : CheckBox, DefaultComponent(
    metadata = componentMetadata,
    renderer = renderingStrategy
),
    Selectable by Selectable.create(),
    TextOverride by TextOverride.create(textProperty) {

    override var checkBoxState: CheckBoxState = UNCHECKED
        private set

    /** `true` between the activation (press) and the deactivation (release). */
    private var pressing = false

    /** `true` while [pressing] and the cursor is outside the box: a release there cancels. */
    private var cursorOutside = false

    init {
        selectedProperty.onChange {
            this.checkBoxState = if (it.newValue) CHECKED else UNCHECKED
        }
    }

    // The box keeps showing the pending state ([+] / [-]) for the whole press, even when the
    // cursor leaves it (the dispatcher keeps delivering the press to it); only the colours
    // follow the hover.
    override fun mouseExited(event: MouseEvent, phase: UIEventPhase): UIEventResponse = whenEnabledRespondWith {
        if (phase == UIEventPhase.TARGET && pressing) cursorOutside = true
        super.mouseExited(event, phase)
    }

    override fun mouseEntered(event: MouseEvent, phase: UIEventPhase): UIEventResponse = whenEnabledRespondWith {
        if (phase == UIEventPhase.TARGET && pressing) cursorOutside = false
        super.mouseEntered(event, phase)
    }

    override fun activated(): UIEventResponse = whenEnabled {
        pressing = true
        cursorOutside = false
        this.checkBoxState = if (isSelected) UNCHECKING else CHECKING
        super.activated()
    }

    override fun deactivated(): UIEventResponse = whenEnabled {
        // Released outside the box: the press is cancelled, like a button in a browser.
        if (pressing && !cursorOutside) {
            isSelected = isSelected.not()
        }
        pressing = false
        this.checkBoxState = if (isSelected) CHECKED else UNCHECKED
        super.deactivated()
    }

    override fun focusTaken(): UIEventResponse = whenEnabled {
        pressing = false
        this.checkBoxState = if (isSelected) CHECKED else UNCHECKED
        super.focusTaken()
    }

    override fun convertColorTheme(colorTheme: ColorTheme): ComponentStyleSet = colorTheme.toInteractiveStyle()

}
