package org.hexworks.zircon.api

import org.hexworks.zircon.api.resource.BuiltInTrueTypeFontResource

object TrueTypeFontResources {

    @JvmStatic
    fun ubuntuMono(height: Int) = BuiltInTrueTypeFontResource.UBUNTU_MONO.toTilesetResource(height)

    @JvmStatic
    fun inconsolata(height: Int) = BuiltInTrueTypeFontResource.INCONSOLATA.toTilesetResource(height)

    @JvmStatic
    fun amstrad(height: Int) = BuiltInTrueTypeFontResource.AMSTRAD.toTilesetResource(height)

    @JvmStatic
    fun amstradNarrow(height: Int) = BuiltInTrueTypeFontResource.AMSTRAD_NARROW.toTilesetResource(height)

    @JvmStatic
    fun ibmBios(height: Int) = BuiltInTrueTypeFontResource.IBM_BIOS.toTilesetResource(height)

    @JvmStatic
    fun ibmBiosNarrow(height: Int) = BuiltInTrueTypeFontResource.IBM_BIOS_NARROW.toTilesetResource(height)

    @JvmStatic
    fun ibmBiosWide(height: Int) = BuiltInTrueTypeFontResource.IBM_BIOS_WIDE.toTilesetResource(height)

    @JvmStatic
    fun tandy(height: Int) = BuiltInTrueTypeFontResource.TANDY.toTilesetResource(height)

    @JvmStatic
    fun tandyNarrow(height: Int) = BuiltInTrueTypeFontResource.TANDY_NARROW.toTilesetResource(height)

    @JvmStatic
    fun kaypro(height: Int) = BuiltInTrueTypeFontResource.KAYPRO.toTilesetResource(height)

    @JvmStatic
    fun kayproNarrow(height: Int) = BuiltInTrueTypeFontResource.KAYPRO_NARROW.toTilesetResource(height)

    @JvmStatic
    fun vtech(height: Int) = BuiltInTrueTypeFontResource.VTECH.toTilesetResource(height)

    @JvmStatic
    fun vtechNarrow(height: Int) = BuiltInTrueTypeFontResource.VTECH_NARROW.toTilesetResource(height)

    @JvmStatic
    fun ibmPs2Narrow(height: Int) = BuiltInTrueTypeFontResource.IBM_PS2_NARROW.toTilesetResource(height)

    @JvmStatic
    fun att(height: Int) = BuiltInTrueTypeFontResource.ATT.toTilesetResource(height)

    @JvmStatic
    fun attNarrow(height: Int) = BuiltInTrueTypeFontResource.ATT_NARROW.toTilesetResource(height)

}
