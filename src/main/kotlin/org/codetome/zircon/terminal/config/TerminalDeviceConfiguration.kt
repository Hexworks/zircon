package org.codetome.zircon.terminal.config

import org.codetome.zircon.TextColor

/**
 * Object that encapsulates the configuration parameters for the terminal device that a
 * [org.codetome.zircon.terminal.swing.SwingTerminalComponent] is emulating.
 * This includes properties such as the shape of the cursor, the color of the cursor
 * and if the cursor should blink or not.
 */
class TerminalDeviceConfiguration(
        var blinkLengthInMilliSeconds: Long = 500,
        var cursorStyle: CursorStyle = CursorStyle.REVERSED,
        var cursorColor: TextColor = TextColor.ANSI.WHITE,
        var isCursorBlinking: Boolean = false,
        var isClipboardAvailable: Boolean = true) {


    companion object {

        @JvmStatic
        fun getDefault() = TerminalDeviceConfiguration()
    }
}