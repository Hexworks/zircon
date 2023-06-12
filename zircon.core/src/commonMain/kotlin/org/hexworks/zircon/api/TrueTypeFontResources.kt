package org.hexworks.zircon.api

import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.resource.BuiltInTrueTypeFontResource
import org.hexworks.zircon.internal.resource.TilesetSourceType
import org.hexworks.zircon.internal.resource.TrueTypeTilesetResource

/**
 * This object can be used to load either built-in true type [TilesetResource]s or external ones.
 */
object TrueTypeFontResources {

    // These fonts come from Google Fonts
    // https://fonts.google.com/
    fun ubuntuMono(height: Int): TilesetResource = BuiltInTrueTypeFontResource.UBUNTU_MONO.toTilesetResource(height)

    fun inconsolata(height: Int): TilesetResource = BuiltInTrueTypeFontResource.INCONSOLATA.toTilesetResource(height)

    /**
     * This font is from the Oldschool PC font pack
     * https://int10h.org/oldschool-pc-fonts/
     */
    fun amstrad(height: Int): TilesetResource = BuiltInTrueTypeFontResource.AMSTRAD.toTilesetResource(height)

    /**
     * This font is from the Oldschool PC font pack
     * https://int10h.org/oldschool-pc-fonts/
     */
    fun amstradNarrow(height: Int): TilesetResource =
        BuiltInTrueTypeFontResource.AMSTRAD_NARROW.toTilesetResource(height)

    /**
     * This font is from the Oldschool PC font pack
     * https://int10h.org/oldschool-pc-fonts/
     */
    fun ibmBios(height: Int): TilesetResource = BuiltInTrueTypeFontResource.IBM_BIOS.toTilesetResource(height)

    /**
     * This font is from the Oldschool PC font pack
     * https://int10h.org/oldschool-pc-fonts/
     */
    fun ibmBiosNarrow(height: Int): TilesetResource =
        BuiltInTrueTypeFontResource.IBM_BIOS_NARROW.toTilesetResource(height)

    /**
     * This font is from the Oldschool PC font pack
     * https://int10h.org/oldschool-pc-fonts/
     */
    fun ibmBiosWide(height: Int): TilesetResource = BuiltInTrueTypeFontResource.IBM_BIOS_WIDE.toTilesetResource(height)

    /**
     * This font is from the Oldschool PC font pack
     * https://int10h.org/oldschool-pc-fonts/
     */
    fun tandy(height: Int): TilesetResource = BuiltInTrueTypeFontResource.TANDY.toTilesetResource(height)

    /**
     * This font is from the Oldschool PC font pack
     * https://int10h.org/oldschool-pc-fonts/
     */
    fun tandyNarrow(height: Int): TilesetResource = BuiltInTrueTypeFontResource.TANDY_NARROW.toTilesetResource(height)

    /**
     * This font is from the Oldschool PC font pack
     * https://int10h.org/oldschool-pc-fonts/
     */
    fun kaypro(height: Int): TilesetResource = BuiltInTrueTypeFontResource.KAYPRO.toTilesetResource(height)

    /**
     * This font is from the Oldschool PC font pack
     * https://int10h.org/oldschool-pc-fonts/
     */
    fun kayproNarrow(height: Int): TilesetResource = BuiltInTrueTypeFontResource.KAYPRO_NARROW.toTilesetResource(height)

    /**
     * This font is from the Oldschool PC font pack
     * https://int10h.org/oldschool-pc-fonts/
     */
    fun vtech(height: Int): TilesetResource = BuiltInTrueTypeFontResource.VTECH.toTilesetResource(height)

    /**
     * This font is from the Oldschool PC font pack
     * https://int10h.org/oldschool-pc-fonts/
     */
    fun vtechNarrow(height: Int): TilesetResource = BuiltInTrueTypeFontResource.VTECH_NARROW.toTilesetResource(height)

    /**
     * This font is from the Oldschool PC font pack
     * https://int10h.org/oldschool-pc-fonts/
     */
    fun ibmPs2Narrow(height: Int): TilesetResource =
        BuiltInTrueTypeFontResource.IBM_PS2_NARROW.toTilesetResource(height)

    /**
     * This font is from the Oldschool PC font pack
     * https://int10h.org/oldschool-pc-fonts/
     */
    fun att(height: Int): TilesetResource = BuiltInTrueTypeFontResource.ATT.toTilesetResource(height)

    /**
     * This font is from the Oldschool PC font pack
     * https://int10h.org/oldschool-pc-fonts/
     */
    fun attWide(height: Int): TilesetResource = BuiltInTrueTypeFontResource.ATT_NARROW.toTilesetResource(height)

    /**
     * Use this function if you want to load a [TilesetResource]
     * from the filesystem.
     */
    fun loadTilesetFromFilesystem(
        width: Int,
        height: Int,
        path: String
    ): TilesetResource {
        return TrueTypeTilesetResource(
            width = width,
            height = height,
            path = path,
            tilesetSourceType = TilesetSourceType.FILESYSTEM
        )
    }

    /**
     * Use this function if you want to load a [TilesetResource]
     * which is bundled into a jar file which you build from
     * your application.
     */
    fun loadTilesetFromJar(
        width: Int,
        height: Int,
        path: String
    ): TilesetResource {
        return TrueTypeTilesetResource(
            width = width,
            height = height,
            path = path,
            tilesetSourceType = TilesetSourceType.JAR
        )
    }
}
