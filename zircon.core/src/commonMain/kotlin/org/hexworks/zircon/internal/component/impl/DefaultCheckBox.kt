package org.hexworks.zircon.internal.component.impl

import org.hexworks.zircon.api.behavior.Selectable
import org.hexworks.zircon.api.behavior.TextOverride
import org.hexworks.zircon.api.component.CheckBox
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.data.ComponentState
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.extensions.whenEnabled
import org.hexworks.zircon.api.extensions.whenEnabledRespondWith
import org.hexworks.zircon.api.uievent.MouseEvent
import org.hexworks.zircon.api.uievent.UIEventPhase
import org.hexworks.zircon.internal.component.impl.DefaultCheckBox.CheckBoxState.CHECKED
import org.hexworks.zircon.internal.component.impl.DefaultCheckBox.CheckBoxState.CHECKING
import org.hexworks.zircon.internal.component.impl.DefaultCheckBox.CheckBoxState.UNCHECKED
import org.hexworks.zircon.internal.component.impl.DefaultCheckBox.CheckBoxState.UNCHECKING
import kotlin.jvm.Synchronized

@Suppress("DuplicatedCode")
class DefaultCheckBox(
    componentMetadata: ComponentMetadata,
    initialText: String,
    override val labelAlignment: CheckBoxAlignment = CheckBoxAlignment.RIGHT,
    renderingStrategy: ComponentRenderingStrategy<CheckBox>
) : CheckBox, DefaultComponent(
    componentMetadata = componentMetadata,
    renderer = renderingStrategy
),
    Selectable by Selectable.create(),
    TextOverride by TextOverride.create(initialText) {

    override var checkBoxState = UNCHECKED
        private set

    private var pressing = false

    init {
        selectedProperty.onChange {
            this.checkBoxState = if (it.newValue) CHECKED else UNCHECKED
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

    override fun convertColorTheme(colorTheme: ColorTheme) = colorTheme.toInteractiveStyle()

    /**
     * Represents the possible states of a [CheckBox].
     */
    enum class CheckBoxState {
        /**
         * Used when an [UNCHECKED] checkbox is active (being pressed/clicked).
         * [CHECKING] is followed by the [CHECKED] state
         * @see ComponentState
         */
        CHECKING,

        /**
         * Used when a [CheckBox] is not being interacted with and it [isSelected]
         * [CHECKED] is followed by the [UNCHECKING] state.
         */
        CHECKED,

        /**
         * Used when a [CHECKED] [CheckBox] is active (being pressed/clicked).
         * [UNCHECKING] is followed by the [UNCHECKED] state.
         * @see ComponentState
         */
        UNCHECKING,

        /**
         * Used when a [CheckBox] is not selected and it is not being interacted
         * with.
         */
        UNCHECKED
    }

    /**
     * Contains the possible options where the check in a [CheckBox]
     * can be placed.
     */
    enum class CheckBoxAlignment {
        LEFT,
        RIGHT
    }
}
