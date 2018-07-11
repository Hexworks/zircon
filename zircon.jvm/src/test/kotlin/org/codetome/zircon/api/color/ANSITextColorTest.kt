package org.codetome.zircon.api.color

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.interop.toAWTColor
import org.junit.Test
import java.awt.Color

class ANSITextColorTest {


    @Test
    fun shouldReturnProperAWTColor() {
        assertThat(ANSITextColor.YELLOW.toAWTColor())
                .isEqualTo(YELLOW)
    }

    @Test
    fun shouldReturnProperRed() {
        assertThat(ANSITextColor.YELLOW.getRed())
                .isEqualTo(RED)
    }

    @Test
    fun shouldReturnProperGreen() {
        assertThat(ANSITextColor.YELLOW.getGreen())
                .isEqualTo(GREEN)
    }

    @Test
    fun shouldReturnProperBlue() {
        assertThat(ANSITextColor.YELLOW.getBlue())
                .isEqualTo(BLUE)
    }

    @Test
    fun shouldReturnProperAlpha() {
        assertThat(ANSITextColor.YELLOW.getAlpha())
                .isEqualTo(ALPHA)
    }

    companion object {
        val RED = 170
        val GREEN = 85
        val BLUE = 0
        val ALPHA = 255
        val YELLOW = Color(RED, GREEN, BLUE, ALPHA)
    }

}
