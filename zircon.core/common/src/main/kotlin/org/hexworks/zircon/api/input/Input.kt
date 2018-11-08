package org.hexworks.zircon.api.input

import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.input.InputType.*
import org.hexworks.zircon.api.util.Maybe
import org.hexworks.zircon.platform.util.SystemUtils


/**
 * Represents a keyboard or a mouse event.
 *
 * Use the <code>inputType</code> field to determine what kind of key was pressed.
 * For ordinary letters, numbers and symbols, the <code>inputType</code> will be <code>InputType.Character</code>
 * and the actual character value of the key is in the <code>character</code> field.
 * Please note that
 * - returnThis (`\n`)
 * - tab (`\t`) and
 * - backspace (`\b`)
 * are not sorted under type
 * <code>InputType.Character</code> but under
 * - <code>InputType.Enter</code>
 * - <code>InputType.Tab</code> and
 * - <code>InputType.Backspace</code>
 * respectively.
 */
sealed class Input(private val inputType: InputType,
                   private val eventTime: Long = SystemUtils.getCurrentTimeMs()) {

    fun inputType() = inputType

    fun inputTypeIs(inputType: InputType) = inputType == this.inputType

    fun getEventTime() = eventTime

    fun isKeyStroke() = this is KeyStroke

    fun isMouseAction() = this is MouseAction

    fun asKeyStroke() = Maybe.ofNullable(this as? KeyStroke)

    fun asMouseAction() = Maybe.ofNullable(this as? MouseAction)

}

data class KeyStroke(
        private val character: Char = ' ',
        private val type: InputType = InputType.Character,
        private val ctrlDown: Boolean = false,
        private val altDown: Boolean = false,
        private val metaDown: Boolean = false,
        private val shiftDown: Boolean = false) : Input(type) {

    fun isCtrlDown() = ctrlDown

    fun isAltDown() = altDown

    fun isShiftDown() = shiftDown

    fun getCharacter() = char

    private val char: Char = when (inputType()) {
        Backspace -> '\b'
        Enter -> '\n'
        Tab -> '\t'
        else -> {
            character
        }
    }

    companion object {
        val EOF_STROKE = KeyStroke(
                character = ' ',
                type = EOF)
    }
}

/**
 * MouseAction, a Input in disguise, this class contains the information of a single mouse action event.
 */
data class MouseAction(
        val actionType: MouseActionType,
        val button: Int,
        val position: Position)
    : Input(
        inputType = InputType.MouseEvent)

