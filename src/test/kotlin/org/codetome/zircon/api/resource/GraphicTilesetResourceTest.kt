package org.codetome.zircon.api.resource

import org.codetome.zircon.internal.DefaultTextCharacter
import org.codetome.zircon.api.factory.TextColorFactory
import org.codetome.zircon.internal.font.impl.PickRandomMetaStrategy
import org.junit.Test


class GraphicTilesetResourceTest {

    @Test
    fun test() {
        val result = GraphicTilesetResource.NETHACK_16X16.toFont(PickRandomMetaStrategy())
        val meta = result.fetchMetadataForChar('b').first()
        val region = result.fetchRegionForChar(
                textCharacter = DefaultTextCharacter.of(meta.char,
                        TextColorFactory.DEFAULT_FOREGROUND_COLOR,
                        TextColorFactory.DEFAULT_BACKGROUND_COLOR),
                tags = *meta.tags.toTypedArray())
    }
}

