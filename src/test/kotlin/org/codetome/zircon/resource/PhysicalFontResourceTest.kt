package org.codetome.zircon.resource

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.PhysicalFontResource
import org.junit.Test

class PhysicalFontResourceTest {


    @Test
    fun shouldLoadFontWithProperHeightWhenSizeIsSpecified() {
        val expectedSize = 25f
        val font = PhysicalFontResource.ANONYMOUS_PRO.asPhysicalFont(expectedSize)

        assertThat(font.getHeight())
                .isEqualTo(expectedSize.toInt())
    }

    @Test
    fun shouldBeAbleToProperlyLoadAllBuiltInFonts() {
        PhysicalFontResource.values().forEach {
            it.asPhysicalFont()
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