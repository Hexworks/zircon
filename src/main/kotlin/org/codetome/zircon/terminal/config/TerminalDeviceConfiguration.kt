package org.codetome.zircon.terminal.config

import org.codetome.zircon.TextColor

/**
 * Object that encapsulates the configuration parameters for the terminal device that a
 * [org.codetome.zircon.terminal.swing.SwingTerminalComponent] is emulating.
 * This includes properties such as the shape of the cursor, the color of the cursor
 * and if the cursor should blink or not.
 */
class TerminalDeviceConfiguration(
        val blinkLengthInMilliSeconds: Long = 500,
        val cursorStyle: CursorStyle = CursorStyle.REVERSED,
        val cursorColor: TextColor = TextColor.ANSI.WHITE,
        val isCursorBlinking: Boolean = false,
        val isClipboardAvailable: Boolean = true) {

    companion object {
        fun getDefault() = TerminalDeviceConfiguration()
    }
}