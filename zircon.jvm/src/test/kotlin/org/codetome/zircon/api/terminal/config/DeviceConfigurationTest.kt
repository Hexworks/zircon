package org.codetome.zircon.api.terminal.config

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.builder.terminal.DeviceConfigurationBuilder
import org.codetome.zircon.api.color.ANSITextColor
import org.codetome.zircon.api.terminal.CursorStyle
import org.junit.Test

class DeviceConfigurationTest {

    @Test
    fun shouldProperlySetValues() {
        val target = DeviceConfigurationBuilder.newBuilder()
                .blinkLengthInMilliSeconds(BLINK_TIME)
                .clipboardAvailable(HAS_CLIPBOARD)
                .cursorBlinking(IS_BLINKING)
                .cursorColor(CURSOR_COLOR)
                .cursorStyle(CURSOR_STYLE)
                .build()

        assertThat(target.blinkLengthInMilliSeconds)
                .isEqualTo(BLINK_TIME)
        assertThat(target.cursorStyle)
                .isEqualTo(CURSOR_STYLE)
        assertThat(target.cursorColor)
                .isEqualTo(CURSOR_COLOR)
        assertThat(target.isCursorBlinking)
                .isEqualTo(IS_BLINKING)
        assertThat(target.isClipboardAvailable)
                .isEqualTo(HAS_CLIPBOARD)
    }

    companion object {
        val BLINK_TIME = 5L
        val CURSOR_STYLE = CursorStyle.UNDER_BAR
        val CURSOR_COLOR = ANSITextColor.GREEN
        val IS_BLINKING = true
        val HAS_CLIPBOARD = true
    }
}
