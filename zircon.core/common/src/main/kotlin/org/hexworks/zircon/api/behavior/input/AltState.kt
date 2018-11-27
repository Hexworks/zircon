package org.hexworks.zircon.api.behavior.input

import org.hexworks.zircon.api.input.KeyStroke

/**
 * Represents the state of the `Ctrl` button.
 */
enum class AltState(private val pressed: Boolean) {
    ALT_UP(false),
    ALT_PRESSED(true);

    fun matches(keyStroke: KeyStroke): Boolean {
        return keyStroke.isAltDown() == pressed
    }
}
