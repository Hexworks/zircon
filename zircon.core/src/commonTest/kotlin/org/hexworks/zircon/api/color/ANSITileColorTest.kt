package org.hexworks.zircon.api.color

import io.kotest.matchers.shouldBe
import org.hexworks.zircon.api.color.palette.ansi.DefaultAnsiPalette
import kotlin.test.Test

class ANSITileColorTest {

    @Test
    fun shouldReturnProperRed() {
        DefaultAnsiPalette[ANSIColor.YELLOW].red shouldBe RED
    }

    @Test
    fun shouldReturnProperGreen() {
        DefaultAnsiPalette[ANSIColor.YELLOW].green shouldBe GREEN
    }

    @Test
    fun shouldReturnProperBlue() {
        DefaultAnsiPalette[ANSIColor.YELLOW].blue shouldBe BLUE
    }

    @Test
    fun shouldReturnProperAlpha() {
        DefaultAnsiPalette[ANSIColor.YELLOW].alpha shouldBe ALPHA
    }

    companion object {
        const val RED = 196
        const val GREEN = 126
        const val BLUE = 0
        const val ALPHA = 255
    }

}
