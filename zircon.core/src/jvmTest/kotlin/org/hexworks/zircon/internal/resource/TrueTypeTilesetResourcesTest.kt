package org.hexworks.zircon.internal.resource

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.TrueTypeFontResources
import org.hexworks.zircon.api.resource.TilesetResource
import org.junit.Test

class TrueTypeTilesetResourcesTest {

    @Test
    fun shouldContainAllFonts() {
        val fontCount = TrueTypeFontResources::class.members
            .filter { it.isFinal }
            .filter { it.parameters.size == 2 }
            .map { accessor ->
                assertThat(accessor.call(TrueTypeFontResources, 20))
                    .describedAs("Font: ${accessor.name}")
                    .isInstanceOf(TilesetResource::class.java)
                1
            }.fold(0, Int::plus)

        assertThat(fontCount).isEqualTo(ENUM_FONTS.size)
    }

    companion object {
        private val ENUM_FONTS = BuiltInTrueTypeFontResource.values().toList()
    }
}
