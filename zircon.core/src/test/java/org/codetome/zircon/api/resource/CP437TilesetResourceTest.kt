package org.codetome.zircon.api.resource

import org.codetome.zircon.internal.font.impl.PickRandomMetaStrategy
import org.junit.Test

class ResourcesTest {

    @Test
    fun shouldBeAbleToAccessStaticMethodsFromJava() {
        REXPaintResource
                .loadREXFile(this.javaClass.classLoader.getResourceAsStream("rex_files/cp437_table.xp"))
        PhysicalFontResource
                .loadPhysicalFont(20f, true, this.javaClass.getResourceAsStream("/monospace_fonts/AnonymousPro-Regular.ttf"))
        GraphicTilesetResource
                .loadGraphicTileset(this.javaClass.getResource("/graphic_tilesets/nethack_16x16.zip").path, PickRandomMetaStrategy())
        CP437TilesetResource
                .loadCP437Tileset(
                        width = 16,
                        height = 16,
                        source = this.javaClass.getResourceAsStream("/cp_437_tilesets/adu_dhabi_16x16.png"))
        CP437TilesetResource
                .convertCp437toUnicode(1)
        CP437TilesetResource
                .fetchCP437IndexForChar('a')
    }
}