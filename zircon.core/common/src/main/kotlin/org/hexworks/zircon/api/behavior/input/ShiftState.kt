package org.hexworks.zircon.api.behavior.input

import org.hexworks.zircon.api.input.KeyStroke

/**
 * Represents the state of the `Ctrl` button.
 */
enum class ShiftState(private val pressed: Boolean) {
    SHIFT_UP(false),
    SHIFT_PRESSED(true);

    fun matches(keyStroke: KeyStroke): Boolean {
        return keyStroke.isShiftDown() == pressed
    }
}
