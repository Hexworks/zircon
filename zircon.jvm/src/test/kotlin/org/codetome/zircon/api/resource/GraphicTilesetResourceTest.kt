package org.codetome.zircon.api.resource

import org.codetome.zircon.api.builder.TextCharacterBuilder
import org.codetome.zircon.api.color.TextColorFactory
import org.codetome.zircon.internal.font.FontLoaderRegistry
import org.codetome.zircon.internal.font.impl.PickRandomMetaStrategy
import org.codetome.zircon.internal.font.impl.TestFontLoader
import org.junit.Test


class GraphicTilesetResourceTest {

    @Test
    fun test() {
        FontLoaderRegistry.setFontLoader(TestFontLoader())
        val result = GraphicTilesetResource.NETHACK_16X16.toFont(PickRandomMetaStrategy())
        val meta = result.fetchMetadataForChar('b').first()
        val region = result.fetchRegionForChar(
                textCharacter = TextCharacterBuilder.newBuilder()
                        .character(meta.char)
                        .tags(meta.tags)
                        .foregroundColor(TextColorFactory.defaultForegroundColor())
                        .backgroundColor(TextColorFactory.defaultBackgroundColor())
                        .build())
    }
}

