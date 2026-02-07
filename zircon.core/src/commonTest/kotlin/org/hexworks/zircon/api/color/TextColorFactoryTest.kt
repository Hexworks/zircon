package org.hexworks.zircon.api.color

import io.kotest.matchers.shouldBe
import org.hexworks.zircon.api.color.palette.ansi.DefaultAnsiPalette
import kotlin.test.Test

class TextColorFactoryTest {

    @Test
    fun shouldGiveBackWhiteAsDefaultForegroundColor() {
        Color.defaultForegroundColor() shouldBe DefaultAnsiPalette[ANSIColor.WHITE]
        Color.defaultBackgroundColor() shouldBe DefaultAnsiPalette[ANSIColor.BLACK]
    }
}
