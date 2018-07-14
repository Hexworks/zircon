package org.codetome.zircon.api.color

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.platform.factory.TextColorFactory
import org.junit.Test

class TextColorFactoryTest {

    @Test
    fun shouldGiveBackWhiteAsDefaultForegroundColor() {
        assertThat(TextColorFactory.defaultForegroundColor()).isEqualTo(ANSITextColor.WHITE)
    }
}
