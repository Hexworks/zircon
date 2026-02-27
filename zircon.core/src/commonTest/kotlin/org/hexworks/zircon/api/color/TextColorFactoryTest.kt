package org.hexworks.zircon.api.color

import io.kotest.matchers.shouldBe
import org.hexworks.zircon.api.color.palette.ansi.DefaultAnsiPalette
import kotlin.test.Test

class TextColorFactoryTest {

    @Test
    fun shouldGiveBackWhiteAsDefaultForegroundColor() {
        Color.DEFAULT_FOREGROUND_COLOR shouldBe DefaultAnsiPalette[ANSIColor.WHITE]
        Color.DEFAULT_BACKGROUND_COLOR shouldBe DefaultAnsiPalette[ANSIColor.BLACK]
    }
}
