package org.hexworks.zircon.internal.component.impl

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
class DefaultCheckBox internal constructor(
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

    override var checkBoxState = UNCHECKED
        private set

    private var pressing = false

    init {
        selectedProperty.onChange {
            this.checkBoxState = if (it.newValue) CHECKED else UNCHECKED
        }
    }

    override fun mouseExited(event: MouseEvent, phase: UIEventPhase) = whenEnabledRespondWith {
        if (phase == UIEventPhase.TARGET) {
            pressing = false
            this.checkBoxState = if (isSelected) CHECKED else UNCHECKED
        }
        super.mouseExited(event, phase)
    }

    override fun activated() = whenEnabled {
        pressing = true
        this.checkBoxState = if (isSelected) UNCHECKING else CHECKING
        super.activated()
    }

    override fun deactivated() = whenEnabled {
        pressing = false
        isSelected = isSelected.not()
        this.checkBoxState = if (isSelected) CHECKED else UNCHECKED
        super.deactivated()
    }

    override fun focusTaken() = whenEnabled {
        pressing = false
        super.focusTaken()
    }

    override fun convertColorTheme(colorTheme: ColorTheme) = colorTheme.toInteractiveStyle()

}
