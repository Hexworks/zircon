package org.hexworks.zircon.internal.resource

import org.hexworks.zircon.api.resource.TilesetResource
import kotlin.jvm.JvmStatic

/**
 * This enum contains the metadata for the built-in True Type fonts.
 */
enum class BuiltInTrueTypeFontResource(private val fileName: String,
                                       private val widthFn: (Int) -> Int) {

    /**
     * These fonts are taken from Google Fonts.
     */
    UBUNTU_MONO("ubuntu_mono", { it.div(2) }),
    INCONSOLATA("inconsolata", { it.div(2) }),
    /**
     * These fonts are part of the Ultimate Oldschool PC Font Pack and licensed under
     * Creative Commons Attribution-ShareAlike 4.0 International License.
     * See: https://int10h.org/oldschool-pc-fonts/readme/
     */
    AMSTRAD("amstrad", { it }),
    AMSTRAD_NARROW("amstrad_2y", { it.div(2) }),
    ATT("att", { it }),
    ATT_NARROW("att_2y", { it.div(2) }),
    IBM_BIOS("ibm_bios", { it }),
    IBM_BIOS_NARROW("ibm_bios_2y", { it.div(2) }),
    IBM_BIOS_WIDE("ibm_bios_2x", { it.times(2) }),
    IBM_PS2_NARROW("ibm_ps2_thin", { it.div(2) }),
    KAYPRO("kaypro", { it }),
    KAYPRO_NARROW("kaypro_2y", { it.div(2) }),
    TANDY("tandy", { it }),
    TANDY_NARROW("tandy_2y", { it.div(2) }),
    VTECH("vtech", { it }),
    VTECH_NARROW("vtech", { it.div(2) });

    fun toTilesetResource(height: Int): TilesetResource = TrueTypeTilesetResource(
            path = "$FONTS_DIR/$fileName.$FONTS_EXT",
            width = widthFn.invoke(height),
            height = height,
            tilesetSourceType = TilesetSourceType.JAR,
            name = name)

    companion object {

        const val FONTS_DIR = "/monospace_fonts"
        const val FONTS_EXT = "ttf"

        @JvmStatic
        fun squareFonts(height: Int): List<TilesetResource> = values()
                .filter { it.widthFn(1) == 1 }
                .map { it.toTilesetResource(height) }

        @JvmStatic
        fun wideFonts(height: Int): List<TilesetResource> = values()
                .filter { it.widthFn(2) > 2 }
                .map { it.toTilesetResource(height) }

        @JvmStatic
        fun narrowFonts(height: Int): List<TilesetResource> = values()
                .filter { it.widthFn(2) < 2 }
                .map { it.toTilesetResource(height) }
    }
}
