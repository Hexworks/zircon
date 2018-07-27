package org.codetome.zircon.api.resource

import org.codetome.zircon.internal.tileset.impl.TilesetLoaderRegistry
import org.codetome.zircon.internal.tileset.impl.PickRandomMetaStrategy
import org.codetome.zircon.internal.tileset.impl.VirtualTilesetLoader
import org.codetome.zircon.internal.util.CP437Utils
import org.junit.Before
import org.junit.Test

class ResourcesTest {

    @Before
    fun setUp() {
        TilesetLoaderRegistry.setFontLoader(VirtualTilesetLoader())
    }

    @Test
    fun shouldBeAbleToLoadResources() {
        REXPaintResource
                .loadREXFile(this.javaClass.getResourceAsStream("/rex_files/cp437_table.xp"))
        GraphicTilesetResource
                .loadGraphicTileset("src/main/resources/graphic_tilesets/nethack_16x16.zip", PickRandomMetaStrategy())
        CP437TilesetResource
                .loadCP437Tileset(
                        width = 16,
                        height = 16,
                        path = "src/main/resources/cp_437_tilesets/adu_dhabi_16x16.png")
        CP437Utils.convertCp437toUnicode(1)
        CP437Utils.fetchCP437IndexForChar('a')
    }
}
