package org.hexworks.zircon.api.component

import org.hexworks.zircon.api.behavior.Selectable
import org.hexworks.zircon.api.behavior.TextOverride
import org.hexworks.zircon.api.component.data.ComponentState

/**
 * A [CheckBox] is a [Selectable] that represents its [Selectable.isSelected]
 * state with a check mark (usually an `x`).
 */
interface CheckBox : Component, Selectable, TextOverride {
    /**
     * Contains the current state of the [CheckBox]
     * @see CheckBoxState
     */
    val checkBoxState: CheckBoxState

    /**
     * Contains the [CheckBoxAlignment].
     * @see CheckBoxAlignment
     */
    val labelAlignment: CheckBoxAlignment

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
