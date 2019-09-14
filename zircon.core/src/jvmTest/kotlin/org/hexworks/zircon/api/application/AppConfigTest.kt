package org.hexworks.zircon.api.application

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.builder.application.AppConfigBuilder
import org.hexworks.zircon.api.color.ANSITileColor
import org.junit.Test

class AppConfigTest {

    @Test
    fun shouldProperlySetValues() {
        val target = AppConfigBuilder.newBuilder()
                .withBlinkLengthInMilliSeconds(BLINK_TIME)
                .withClipboardAvailable(HAS_CLIPBOARD)
                .withCursorBlinking(IS_BLINKING)
                .withCursorColor(CURSOR_COLOR)
                .withCursorStyle(CURSOR_STYLE)
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
        val CURSOR_COLOR = ANSITileColor.GREEN
        val IS_BLINKING = true
        val HAS_CLIPBOARD = true
    }
}
