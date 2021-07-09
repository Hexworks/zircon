package org.hexworks.zircon.api.builder.fragment

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.Fragments
import org.hexworks.zircon.api.resource.TilesetResource
import org.junit.Test

class TilesetSelectorBuilderTest {

    @Test
    fun `all tilesets need to have the correct size`() {
        listOf(
            CP437TilesetResources.acorn8X16(),
            CP437TilesetResources.anikki16x16(),
            CP437TilesetResources.bisasam24x24(),
            CP437TilesetResources.rexPaint8x8()
        )
            .forEach { checkTileset(it) }
    }

    private fun checkTileset(tileset: TilesetResource) {
        val expectedWidth = tileset.width
        val expectedHeight = tileset.height
        val testComponent = Components.label().withText("Hello World").withTileset(tileset).build()

        val tilesetSelector = Fragments.tilesetSelector(testComponent.size.width, tileset)
            .withTilesetOverrides(testComponent)
            .build()

        assertThat(tilesetSelector.values).allMatch {
            it.width == expectedWidth && it.height == expectedHeight
        }
    }
}
