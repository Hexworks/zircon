package org.codetome.zircon.api.color

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class TextColorFactoryTest {

    @Test
    fun shouldGiveBackWhiteAsDefaultForegroundColor() {
        assertThat(TextColorFactory.DEFAULT_FOREGROUND_COLOR).isEqualTo(ANSITextColor.WHITE)
    }
}