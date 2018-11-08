package org.hexworks.zircon.internal.color

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.color.TileColor
import org.junit.Test

class TextColorTest {

    @Test
    fun shouldProperlyParseColorFromEnumNameWhenFromStringIsCalled() {
        val result = TileColor.fromString("BLUE")

        assertThat(result).isEqualTo(ANSITileColor.BLUE)
    }

    @Test
    fun shouldProperlyParseColorFromUppercaseCssFormatNameWhenFromStringIsCalled() {
        val result = TileColor.fromString("#AA4455")

        assertThat(result).isEqualTo(EXPECTED_RESULT_COLOR)
    }

    @Test
    fun shouldProperlyParseColorFromLowercaseCssFormatNameWhenFromStringIsCalled() {
        val result = TileColor.fromString("#aa4455")

        assertThat(result).isEqualTo(EXPECTED_RESULT_COLOR)
    }

    @Test(expected = IllegalArgumentException::class)
    fun shouldFailToParseFromMangledCssFormatWhenFromStringIsCalled() {
        TileColor.fromString("#xx4455")
    }

    @Test(expected = IllegalArgumentException::class)
    fun shouldFailToParseWithUnknownEnumNameWhenFromStringIsCalled() {
        TileColor.fromString("wtf-blue")
    }

    @Test
    fun shouldProperlyCreateFromRedGreenBlueWhenCreateFromIsCalled() {
        assertThat(TileColor.create(
                red = EXPECTED_RESULT_COLOR.red,
                green = EXPECTED_RESULT_COLOR.green,
                blue = EXPECTED_RESULT_COLOR.blue))
                .isEqualTo(EXPECTED_RESULT_COLOR)
    }

    companion object {
        val EXPECTED_RESULT_COLOR = DefaultTileColor(170, 68, 85)
    }
}
