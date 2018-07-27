package org.codetome.zircon.api.resource

import org.codetome.zircon.api.builder.data.TileBuilder
import org.codetome.zircon.api.color.TextColor
import org.codetome.zircon.internal.tileset.impl.TilesetLoaderRegistry
import org.codetome.zircon.internal.tileset.impl.PickRandomMetaStrategy
import org.codetome.zircon.internal.tileset.impl.TestTilesetLoader
import org.junit.Test


class GraphicTilesetResourceTest {

    @Test
    fun test() {
        TilesetLoaderRegistry.setFontLoader(TestTilesetLoader())
        val result = GraphicTilesetResource.loadGraphicTileset(
                path = "src/main/resources/graphic_tilesets/nethack_16x16.zip",
                metadataPickingStrategy = PickRandomMetaStrategy())
        val meta = result.fetchMetadataForChar('b').first()
        result.fetchRegionForChar(
                tile = TileBuilder.newBuilder()
                        .character(meta.char)
                        .tags(meta.tags)
                        .foregroundColor(TextColor.defaultForegroundColor())
                        .backgroundColor(TextColor.defaultBackgroundColor())
                        .build())
    }
}

