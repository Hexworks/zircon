package org.hexworks.zircon.internal.resource

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.GraphicalTilesetResources
import org.hexworks.zircon.api.resource.TilesetResource
import org.junit.Test

class GraphicalTilesetResourcesTest {

    @Test
    fun shouldContainAllGraphicTilesets() {
        val fontCount = GraphicalTilesetResources::class.members
                .filter { it.isFinal }
                .filter { it.parameters.size == 1 }
                .map { accessor ->
                    assertThat(accessor.call(GraphicalTilesetResources))
                            .describedAs("Graphic Tileset: ${accessor.name}")
                            .isInstanceOf(TilesetResource::class.java)
                    1
                }.reduce(Int::plus)

        assertThat(fontCount).isEqualTo(ENUM_GRAPHIC_TILESETS.size)
    }

    companion object {
        private val ENUM_GRAPHIC_TILESETS = BuiltInGraphicalTilesetResource.values()
    }
}
