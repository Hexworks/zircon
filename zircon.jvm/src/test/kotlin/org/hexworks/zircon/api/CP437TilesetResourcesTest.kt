package org.hexworks.zircon.api

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.resource.BuiltInCP437TilesetResource
import org.hexworks.zircon.api.resource.TilesetResource
import org.junit.Test

class CP437TilesetResourcesTest {

    @Test
    fun shouldContainAllCP437Tilesets() {
        val fontCount = CP437TilesetResources::class.members
                .filter { it.isFinal }
                .map { accessor ->
                    assertThat(accessor.call(CP437TilesetResources))
                            .describedAs("CP437 Tileset: ${accessor.name}")
                            .isInstanceOf(TilesetResource::class.java)
                    1
                }.reduce(Int::plus)

        assertThat(fontCount).isEqualTo(ENUM_CP437_TILESETS.size)
    }

    companion object {
        val ENUM_CP437_TILESETS = BuiltInCP437TilesetResource.values()
    }
}
