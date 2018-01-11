package org.codetome.zircon.api.resource

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class PhysicalFontResourceTest {


    @Test
    fun shouldLoadFontWithProperHeightWhenSizeIsSpecified() {
        val expectedSize = 25f
        val font = PhysicalFontResource.ANONYMOUS_PRO.toFont(expectedSize)

        assertThat(font.getHeight())
                .isEqualTo(expectedSize.toInt())
    }

    @Test
    fun shouldBeAbleToProperlyLoadAllBuiltInFonts() {
        PhysicalFontResource.values().forEach {
            it.toFont()
        }
    }

    @Test(expected = IllegalArgumentException::class)
    fun shouldNotLoadNonMonospaceFont() {
        PhysicalFontResource.loadPhysicalFont(20f, true, this.javaClass.getResourceAsStream(NON_MONOSPACE_FONT_PATH))
    }

    companion object {
        val NON_MONOSPACE_FONT_PATH = "/non_mono_font/OpenSans-Regular.ttf"
    }
}