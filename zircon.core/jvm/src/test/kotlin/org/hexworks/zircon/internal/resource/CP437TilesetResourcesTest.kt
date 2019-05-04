package org.hexworks.zircon.internal.resource

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.resource.TilesetResource
import org.junit.Test

class CP437TilesetResourcesTest {

    @Test
    fun shouldContainAllCP437Tilesets() {
        val fontCount = CP437TilesetResources::class.members
                .filter { it.isFinal }
                .filter { it.parameters.size == 1 }
                .map { accessor ->
                    assertThat(accessor.call(CP437TilesetResources))
                            .describedAs("CP437 Tileset: ${accessor.name}")
                            .isInstanceOf(TilesetResource::class.java)
                    1
                }.fold(0, Int::plus)

        assertThat(fontCount).isEqualTo(ENUM_CP437_TILESETS.size)
    }

    companion object {
        private val ENUM_CP437_TILESETS = BuiltInCP437TilesetResource.values()
    }
}
