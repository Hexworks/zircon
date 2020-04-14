package org.hexworks.zircon.internal.component.impl

import org.hexworks.zircon.api.behavior.Selectable
import org.hexworks.zircon.api.behavior.TextHolder
import org.hexworks.zircon.api.component.CheckBox
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.extensions.whenEnabled
import org.hexworks.zircon.api.extensions.whenEnabledRespondWith
import org.hexworks.zircon.api.uievent.MouseEvent
import org.hexworks.zircon.api.uievent.UIEventPhase
import org.hexworks.zircon.internal.component.impl.DefaultCheckBox.CheckBoxState.*
import kotlin.jvm.Synchronized

@Suppress("DuplicatedCode")
class DefaultCheckBox(
        componentMetadata: ComponentMetadata,
        initialText: String,
        renderingStrategy: ComponentRenderingStrategy<CheckBox>
) : CheckBox, DefaultComponent(
        componentMetadata = componentMetadata,
        renderer = renderingStrategy),
        Selectable by Selectable.create(),
        TextHolder by TextHolder.create(initialText) {

    override var checkBoxState = UNCHECKED
        private set

    private var pressing = false

    init {
        render()
        textProperty.onChange {
            render()
        }
        selectedProperty.onChange {
            this.checkBoxState = if (it.newValue) CHECKED else UNCHECKED
            render()
        }
    }

    @Synchronized
    override fun mouseExited(event: MouseEvent, phase: UIEventPhase) = whenEnabledRespondWith {
        if (phase == UIEventPhase.TARGET) {
            pressing = false
            this.checkBoxState = if (isSelected) CHECKED else UNCHECKED
        }
        super.mouseExited(event, phase)
    }

    @Synchronized
    override fun activated() = whenEnabled {
        pressing = true
        this.checkBoxState = if (isSelected) UNCHECKING else CHECKING
        render()
        super.activated()
    }

    @Synchronized
    override fun deactivated() = whenEnabled {
        pressing = false
        isSelected = isSelected.not()
        this.checkBoxState = if (isSelected) CHECKED else UNCHECKED
        super.deactivated()
    }

    @Synchronized
    override fun focusTaken() = whenEnabled {
        pressing = false
        super.focusTaken()
    }

    override fun convertColorTheme(colorTheme: ColorTheme) = colorTheme.toInteractableStyle()

    enum class CheckBoxState {
        CHECKING,
        CHECKED,
        UNCHECKING,
        UNCHECKED
    }
}
