package org.codetome.zircon.api.terminal.config

import org.codetome.zircon.api.color.TextColor

/**
 * Object that encapsulates the configuration parameters for the terminal device that a
 * [org.codetome.zircon.api.terminal.Terminal] is emulating.
 * This includes properties such as the shape of the cursor, the color of the cursor
 * and if the cursor should blink or not.
 */
data class DeviceConfiguration(
        val blinkLengthInMilliSeconds: Long,
        val cursorStyle: CursorStyle,
        val cursorColor: TextColor,
        val isCursorBlinking: Boolean,
        val isClipboardAvailable: Boolean)
