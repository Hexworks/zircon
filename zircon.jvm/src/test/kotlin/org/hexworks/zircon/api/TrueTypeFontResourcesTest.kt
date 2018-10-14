package org.hexworks.zircon.api

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.resource.BuiltInTrueTypeFontResource
import org.hexworks.zircon.api.resource.TilesetResource
import org.junit.Test

class TrueTypeFontResourcesTest {

    @Test
    fun shouldContainAllFonts() {
        val fontCount = TrueTypeFontResources::class.members
                .filter { it.isFinal }
                .map { accessor ->
                    assertThat(accessor.call(TrueTypeFontResources, 20))
                            .describedAs("Font: ${accessor.name}")
                            .isInstanceOf(TilesetResource::class.java)
                    1
                }.reduce(Int::plus)

        assertThat(fontCount).isEqualTo(ENUM_FONTS.size)
    }

    companion object {
        val ENUM_FONTS = BuiltInTrueTypeFontResource.values().toList()
    }
}
