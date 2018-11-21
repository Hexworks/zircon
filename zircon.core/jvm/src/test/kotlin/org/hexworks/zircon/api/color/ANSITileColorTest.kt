package org.hexworks.zircon.api.color

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.extensions.toAWTColor
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
        assertThat(ANSITileColor.YELLOW.red)
                .isEqualTo(RED)
    }

    @Test
    fun shouldReturnProperGreen() {
        assertThat(ANSITileColor.YELLOW.green)
                .isEqualTo(GREEN)
    }

    @Test
    fun shouldReturnProperBlue() {
        assertThat(ANSITileColor.YELLOW.blue)
                .isEqualTo(BLUE)
    }

    @Test
    fun shouldReturnProperAlpha() {
        assertThat(ANSITileColor.YELLOW.alpha)
                .isEqualTo(ALPHA)
    }

    companion object {
        const val RED = 128
        const val GREEN = 128
        const val BLUE = 0
        const val ALPHA = 255
        val YELLOW = Color(RED, GREEN, BLUE, ALPHA)
    }

}
