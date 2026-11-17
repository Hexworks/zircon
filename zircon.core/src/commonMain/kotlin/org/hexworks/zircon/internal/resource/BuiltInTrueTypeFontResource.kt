package org.hexworks.zircon.internal.resource

import org.hexworks.zircon.api.behavior.HasTitle
import org.hexworks.zircon.api.resource.ResourceType
import org.hexworks.zircon.api.resource.TilesetResource

/**
 * This enum contains the metadata for the built-in True Type fonts.
 */
@Suppress("SpellCheckingInspection")
public enum class BuiltInTrueTypeFontResource(
    private val fileName: String,
    override val title: String,
    private val ratioFn: (Int) -> Int
): HasTitle {

    /**
     * These fonts are part of the Ultimate Oldschool PC Font Pack and licensed under
     * Creative Commons Attribution-ShareAlike 4.0 International License.
     * See: https://int10h.org/oldschool-pc-fonts/readme/
     */
    AMSTRAD("amstrad", "Amstrad", { it }),
    AMSTRAD_NARROW("amstrad_2y", "Amstrad Narrow", { it.div(2) }),
    ATT_NARROW("att_2y", "ATT Narrow", { it.div(2) }),
    IBM_BIOS("ibm_bios", "IBM Bios", { it }),
    IBM_BIOS_NARROW("ibm_bios_2y", "IBM Bios Narrow", { it.div(2) }),
    IBM_BIOS_WIDE("ibm_bios_2x", "IBM Bios Wide", { it.times(2) }),
    IBM_PS2_NARROW("ibm_ps2_thin", "IBM PS2 Narrow", { it.div(2) }),
    KAYPRO("kaypro", "Kaypro", { it }),
    KAYPRO_NARROW("kaypro_2y", "Kaypro Narrow", { it.div(2) }),
    TANDY("tandy", "Tandy", { it }),
    TANDY_NARROW("tandy_2y", "Tandy Narrow", { it.div(2) }),
    VTECH("vtech", "VTech", { it });

    public fun toTilesetResource(height: Int): TilesetResource = TrueTypeTilesetResource(
        path = "$FONTS_DIR/$fileName.$FONTS_EXT",
        width = ratioFn(height),
        height = height,
        resourceType = ResourceType.PROJECT,
        title = title
    )

    public companion object {

        public const val FONTS_DIR: String = "/monospace_fonts"
        public const val FONTS_EXT: String = "ttf"

        public fun squareFonts(height: Int): List<TilesetResource> = entries
            .filter { it.ratioFn(1) == 1 }
            .map { it.toTilesetResource(height) }

        public fun wideFonts(height: Int): List<TilesetResource> = entries
            .filter { it.ratioFn(2) > 2 }
            .map { it.toTilesetResource(height) }

        public fun narrowFonts(height: Int): List<TilesetResource> = entries
            .filter { it.ratioFn(2) < 2 }
            .map { it.toTilesetResource(height) }
    }
}
