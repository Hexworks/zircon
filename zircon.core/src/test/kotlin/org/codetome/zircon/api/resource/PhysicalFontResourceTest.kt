package org.codetome.zircon.api.resource

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.internal.font.FontLoaderRegistry
import org.codetome.zircon.internal.font.impl.VirtualFontLoader
import org.junit.Before
import org.junit.Test

class PhysicalFontResourceTest {

    @Before
    fun setUp() {
        FontLoaderRegistry.setFontLoader(VirtualFontLoader())
    }

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
