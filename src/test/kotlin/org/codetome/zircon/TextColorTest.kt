package org.codetome.zircon

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.TextColor.*
import org.junit.Test

class TextColorTest {

    @Test
    fun shouldProperlyParseColorFromEnumNameWhenFromStringIsCalled() {
        val result = TextColor.fromString("BLUE")

        assertThat(result).isEqualTo(ANSI.BLUE)
    }

    @Test
    fun shouldProperlyParseColorFromUppercaseCssFormatNameWhenFromStringIsCalled() {
        val result = TextColor.fromString("#AA4455")

        assertThat(result).isEqualTo(EXPECTED_RESULT_COLOR)
    }

    @Test
    fun shouldProperlyParseColorFromLowercaseCssFormatNameWhenFromStringIsCalled() {
        val result = TextColor.fromString("#aa4455")

        assertThat(result).isEqualTo(EXPECTED_RESULT_COLOR)
    }

    @Test(expected = IllegalArgumentException::class)
    fun shouldFailToParseFromMangledCssFormatWhenFromStringIsCalled() {
        TextColor.fromString("#xx4455")
    }

    @Test(expected = IllegalArgumentException::class)
    fun shouldFailToParseWithUnknownEnumNameWhenFromStringIsCalled() {
        TextColor.fromString("wtf-blue")
    }

    @Test
    fun shouldProperlyCreateFromRedGreenBlueWhenCreateFromIsCalled() {
        assertThat(TextColor.createFrom(
                red = EXPECTED_RESULT_COLOR.getRed(),
                green = EXPECTED_RESULT_COLOR.getGreen(),
                blue = EXPECTED_RESULT_COLOR.getBlue()))
                .isEqualTo(EXPECTED_RESULT_COLOR)
    }

    companion object {
        val EXPECTED_RESULT_COLOR = TextColorImpl(170, 68, 85)
    }
}