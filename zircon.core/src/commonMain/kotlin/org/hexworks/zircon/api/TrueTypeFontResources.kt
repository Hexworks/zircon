package org.hexworks.zircon.api

import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.resource.BuiltInTrueTypeFontResource
import org.hexworks.zircon.internal.resource.TilesetSourceType
import org.hexworks.zircon.internal.resource.TrueTypeTilesetResource
import kotlin.jvm.JvmStatic

/**
 * This object can be used to load either built-in true type
 * [TilesetResource]s or external ones.
 */
object TrueTypeFontResources {

    // These fonts come from Google Fonts
    // https://fonts.google.com/
    @JvmStatic
    fun ubuntuMono(height: Int): TilesetResource = BuiltInTrueTypeFontResource.UBUNTU_MONO.toTilesetResource(height)

    @JvmStatic
    fun inconsolata(height: Int): TilesetResource = BuiltInTrueTypeFontResource.INCONSOLATA.toTilesetResource(height)

    // These fonts are from the Oldschool PC font pack
    // https://int10h.org/oldschool-pc-fonts/
    @JvmStatic
    fun amstrad(height: Int): TilesetResource = BuiltInTrueTypeFontResource.AMSTRAD.toTilesetResource(height)

    @JvmStatic
    fun amstradNarrow(height: Int): TilesetResource = BuiltInTrueTypeFontResource.AMSTRAD_NARROW.toTilesetResource(height)

    @JvmStatic
    fun ibmBios(height: Int): TilesetResource = BuiltInTrueTypeFontResource.IBM_BIOS.toTilesetResource(height)

    @JvmStatic
    fun ibmBiosNarrow(height: Int): TilesetResource = BuiltInTrueTypeFontResource.IBM_BIOS_NARROW.toTilesetResource(height)

    @JvmStatic
    fun ibmBiosWide(height: Int): TilesetResource = BuiltInTrueTypeFontResource.IBM_BIOS_WIDE.toTilesetResource(height)

    @JvmStatic
    fun tandy(height: Int): TilesetResource = BuiltInTrueTypeFontResource.TANDY.toTilesetResource(height)

    @JvmStatic
    fun tandyNarrow(height: Int): TilesetResource = BuiltInTrueTypeFontResource.TANDY_NARROW.toTilesetResource(height)

    @JvmStatic
    fun kaypro(height: Int): TilesetResource = BuiltInTrueTypeFontResource.KAYPRO.toTilesetResource(height)

    @JvmStatic
    fun kayproNarrow(height: Int): TilesetResource = BuiltInTrueTypeFontResource.KAYPRO_NARROW.toTilesetResource(height)

    @JvmStatic
    fun vtech(height: Int): TilesetResource = BuiltInTrueTypeFontResource.VTECH.toTilesetResource(height)

    @JvmStatic
    fun vtechNarrow(height: Int): TilesetResource = BuiltInTrueTypeFontResource.VTECH_NARROW.toTilesetResource(height)

    @JvmStatic
    fun ibmPs2Narrow(height: Int): TilesetResource = BuiltInTrueTypeFontResource.IBM_PS2_NARROW.toTilesetResource(height)

    @JvmStatic
    fun att(height: Int): TilesetResource = BuiltInTrueTypeFontResource.ATT.toTilesetResource(height)

    @JvmStatic
    fun attWide(height: Int): TilesetResource = BuiltInTrueTypeFontResource.ATT_NARROW.toTilesetResource(height)

    /**
     * Use this function if you want to load a [TilesetResource]
     * from the filesystem.
     */
    @JvmStatic
    fun loadTilesetFromFilesystem(
            width: Int,
            height: Int,
            path: String): TilesetResource {
        return TrueTypeTilesetResource(
                width = width,
                height = height,
                path = path,
                tilesetSourceType = TilesetSourceType.FILESYSTEM)
    }

    /**
     * Use this function if you want to load a [TilesetResource]
     * which is bundled into a jar file which you build from
     * your application.
     */
    @JvmStatic
    fun loadTilesetFromJar(
            width: Int,
            height: Int,
            path: String): TilesetResource {
        return TrueTypeTilesetResource(
                width = width,
                height = height,
                path = path,
                tilesetSourceType = TilesetSourceType.JAR)
    }
}
