package org.codetome.zircon.api.resource

import org.codetome.zircon.api.builder.TextCharacterBuilder
import org.codetome.zircon.api.color.TextColor
import org.codetome.zircon.platform.factory.TextColorFactory
import org.codetome.zircon.internal.font.impl.FontLoaderRegistry
import org.codetome.zircon.internal.font.impl.PickRandomMetaStrategy
import org.codetome.zircon.internal.font.impl.TestFontLoader
import org.junit.Test


class GraphicTilesetResourceTest {

    @Test
    fun test() {
        FontLoaderRegistry.setFontLoader(TestFontLoader())
        val result = GraphicTilesetResource.loadGraphicTileset(
                path = "src/main/resources/graphic_tilesets/nethack_16x16.zip",
                metadataPickingStrategy = PickRandomMetaStrategy())
        val meta = result.fetchMetadataForChar('b').first()
        result.fetchRegionForChar(
                textCharacter = TextCharacterBuilder.newBuilder()
                        .character(meta.char)
                        .tags(meta.tags)
                        .foregroundColor(TextColor.defaultForegroundColor())
                        .backgroundColor(TextColor.defaultBackgroundColor())
                        .build())
    }
}

