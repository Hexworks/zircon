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
}