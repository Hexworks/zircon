package org.codetome.zircon.terminal.config

import org.codetome.zircon.TextColor
import org.codetome.zircon.builder.DeviceConfigurationBuilder

/**
 * Object that encapsulates the configuration parameters for the terminal device that a
 * [org.codetome.zircon.terminal.Terminal] is emulating.
 * This includes properties such as the shape of the cursor, the color of the cursor
 * and if the cursor should blink or not.
 */
class DeviceConfiguration(
        var blinkLengthInMilliSeconds: Long,
        var cursorStyle: CursorStyle,
        var cursorColor: TextColor,
        var isCursorBlinking: Boolean,
        var isClipboardAvailable: Boolean) {

    companion object {

        @JvmStatic
        fun builder() = DeviceConfigurationBuilder.newBuilder()
    }
}