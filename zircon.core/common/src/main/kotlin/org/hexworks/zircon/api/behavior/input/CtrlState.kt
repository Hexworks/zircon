package org.hexworks.zircon.api.behavior.input

import org.hexworks.zircon.api.input.KeyStroke

/**
 * Represents the state of the `Ctrl` button.
 */
enum class CtrlState(private val pressed: Boolean) {
    CTRL_UP(false),
    CTRL_PRESSED(true);

    fun matches(keyStroke: KeyStroke): Boolean {
        return keyStroke.isCtrlDown() == pressed
    }
}
