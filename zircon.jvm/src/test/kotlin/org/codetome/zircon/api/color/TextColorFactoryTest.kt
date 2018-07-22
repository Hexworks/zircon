package org.codetome.zircon.api.color

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class TextColorFactoryTest {

    @Test
    fun shouldGiveBackWhiteAsDefaultForegroundColor() {
        assertThat(TextColor.defaultForegroundColor()).isEqualTo(ANSITextColor.WHITE)
    }
}
