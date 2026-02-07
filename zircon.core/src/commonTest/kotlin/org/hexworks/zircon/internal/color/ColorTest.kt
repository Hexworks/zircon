package org.hexworks.zircon.internal.color

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.hexworks.zircon.api.color.ANSIColor
import org.hexworks.zircon.api.color.Color
import org.hexworks.zircon.api.color.palette.ansi.DefaultAnsiPalette
import kotlin.test.Test

class ColorTest {

    @Test
    fun shouldThrowExceptionWhenParsingInvalidFormat() {
        shouldThrow<IllegalArgumentException> {
            Color.fromString("BLUE")
        }
    }

    @Test
    fun shouldProperlyParseColorFromUppercaseCssFormatNameWhenFromStringIsCalled() {
        val result = Color.fromString("#AA4455")

        result shouldBe EXPECTED_RESULT_COLOR
    }

    @Test
    fun shouldProperlyParseColorFromLowercaseCssFormatNameWhenFromStringIsCalled() {
        val result = Color.fromString("#aa4455")

        result shouldBe EXPECTED_RESULT_COLOR
    }

    @Test
    fun shouldFailToParseFromMangledCssFormatWhenFromStringIsCalled() {
        shouldThrow<IllegalArgumentException> {
            Color.fromString("#xx4455")
        }
    }

    @Test
    fun shouldFailToParseWithUnknownEnumNameWhenFromStringIsCalled() {
        shouldThrow<IllegalArgumentException> {
            Color.fromString("wtf-blue")
        }
    }

    @Test
    fun shouldProperlyCreateFromRedGreenBlueWhenCreateFromIsCalled() {
        Color.create(
            red = EXPECTED_RESULT_COLOR.red,
            green = EXPECTED_RESULT_COLOR.green,
            blue = EXPECTED_RESULT_COLOR.blue
        ) shouldBe EXPECTED_RESULT_COLOR
    }

    companion object {
        private val EXPECTED_RESULT_COLOR = DefaultColor(170, 68, 85)
    }
}
