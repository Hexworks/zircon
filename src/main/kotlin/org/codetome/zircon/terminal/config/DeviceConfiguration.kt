package org.codetome.zircon.terminal.config

import org.codetome.zircon.TextColor
import org.codetome.zircon.api.DeviceConfigurationBuilder

/**
 * Object that encapsulates the configuration parameters for the terminal device that a
 * [org.codetome.zircon.terminal.Terminal] is emulating.
 * This includes properties such as the shape of the cursor, the color of the cursor
 * and if the cursor should blink or not.
 */
data class DeviceConfiguration(
        val blinkLengthInMilliSeconds: Long,
        val cursorStyle: CursorStyle,
        val cursorColor: TextColor,
        val isCursorBlinking: Boolean,
        val isClipboardAvailable: Boolean) {

    companion object {

        @JvmStatic
        fun builder() = DeviceConfigurationBuilder.newBuilder()
    }
}