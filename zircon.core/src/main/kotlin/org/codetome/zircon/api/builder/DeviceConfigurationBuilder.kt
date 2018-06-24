package org.codetome.zircon.api.builder

import org.codetome.zircon.api.color.TextColor
import org.codetome.zircon.api.color.TextColorFactory
import org.codetome.zircon.api.terminal.CursorStyle
import org.codetome.zircon.api.terminal.DeviceConfiguration

/**
 * Builder for [DeviceConfiguration]s.
 */
data class DeviceConfigurationBuilder(
        private var blinkLengthInMilliSeconds: Long = 500,
        private var cursorStyle: CursorStyle = CursorStyle.USE_CHARACTER_FOREGROUND,
        private var cursorColor: TextColor = TextColorFactory.defaultForegroundColor(),
        private var cursorBlinking: Boolean = false,
        private var clipboardAvailable: Boolean = true) : Builder<DeviceConfiguration> {

    override fun build() = DeviceConfiguration(
            blinkLengthInMilliSeconds = blinkLengthInMilliSeconds,
            cursorStyle = cursorStyle,
            cursorColor = cursorColor,
            isCursorBlinking = cursorBlinking,
            isClipboardAvailable = clipboardAvailable)

    override fun createCopy() = copy()

    /**
     * Sets the length of a blink. All blinking characters will use this setting.
     */
    fun blinkLengthInMilliSeconds(blinkLengthInMilliSeconds: Long) = also {
        this.blinkLengthInMilliSeconds = blinkLengthInMilliSeconds
    }

    /**
     * Sets the cursor style. See: [CursorStyle].
     */
    fun cursorStyle(cursorStyle: CursorStyle) = also {
        this.cursorStyle = cursorStyle
    }

    /**
     * Sets the color of the cursor.
     */
    fun cursorColor(cursorColor: TextColor) = also {
        this.cursorColor = cursorColor
    }

    /**
     * Sets whether the cursor blinks or not.
     */
    fun cursorBlinking(cursorBlinking: Boolean) = also {
        this.cursorBlinking = cursorBlinking
    }

    /**
     * Enables or disables clipboard. <code>Shift + Insert</code> will paste text
     * at the cursor location if clipboard is available.
     */
    fun clipboardAvailable(clipboardAvailable: Boolean) = also {
        this.clipboardAvailable = clipboardAvailable
    }

    companion object {

        fun newBuilder() = DeviceConfigurationBuilder()

        val DEFAULT = newBuilder().build()
    }
}
