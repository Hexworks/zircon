package org.codetome.zircon.api.resource

import org.codetome.zircon.internal.font.FontLoaderRegistry
import org.codetome.zircon.internal.font.impl.PickRandomMetaStrategy
import org.codetome.zircon.internal.font.impl.VirtualFontLoader
import org.codetome.zircon.internal.util.CP437Utils
import org.junit.Before
import org.junit.Test

class ResourcesTest {

    @Before
    fun setUp() {
        FontLoaderRegistry.setFontLoader(VirtualFontLoader())
    }

    @Test
    fun shouldBeAbleToLoadResources() {
        REXPaintResource
                .loadREXFile(this.javaClass.getResourceAsStream("/rex_files/cp437_table.xp"))
        PhysicalFontResource
                .loadPhysicalFont(20f, this.javaClass.getResourceAsStream("/monospace_fonts/AnonymousPro-Regular.ttf"))
        GraphicTilesetResource
                .loadGraphicTileset(this.javaClass.getResourceAsStream("/graphic_tilesets/nethack_16x16.zip"), PickRandomMetaStrategy())
        CP437TilesetResource
                .loadCP437Tileset(
                        width = 16,
                        height = 16,
                        source = this.javaClass.getResourceAsStream("/cp_437_tilesets/adu_dhabi_16x16.png"))
        CP437Utils.convertCp437toUnicode(1)
        CP437Utils.fetchCP437IndexForChar('a')
    }
}
