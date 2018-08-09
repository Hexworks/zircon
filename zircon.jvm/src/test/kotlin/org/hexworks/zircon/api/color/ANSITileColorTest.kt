package org.hexworks.zircon.api.color

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.toAWTColor
import org.junit.Test
import java.awt.Color

class ANSITileColorTest {


    @Test
    fun shouldReturnProperAWTColor() {
        assertThat(ANSITileColor.YELLOW.toAWTColor())
                .isEqualTo(YELLOW)
    }

    @Test
    fun shouldReturnProperRed() {
        assertThat(ANSITileColor.YELLOW.getRed())
                .isEqualTo(RED)
    }

    @Test
    fun shouldReturnProperGreen() {
        assertThat(ANSITileColor.YELLOW.getGreen())
                .isEqualTo(GREEN)
    }

    @Test
    fun shouldReturnProperBlue() {
        assertThat(ANSITileColor.YELLOW.getBlue())
                .isEqualTo(BLUE)
    }

    @Test
    fun shouldReturnProperAlpha() {
        assertThat(ANSITileColor.YELLOW.getAlpha())
                .isEqualTo(ALPHA)
    }

    companion object {
        const val RED = 170
        const val GREEN = 85
        const val BLUE = 0
        const val ALPHA = 255
        val YELLOW = Color(RED, GREEN, BLUE, ALPHA)
    }

}
