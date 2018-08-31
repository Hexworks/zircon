package org.hexworks.zircon.api.resource

import org.hexworks.zircon.api.util.Identifier

/**
 * This enum contains the metadata for the built-in CP437 tilesets.
 */
enum class BuiltInMonospaceFontResource(private val fontName: String,
                                        override val width: Int,
                                        override val height: Int,
                                        private val fileName: String = "$fontName.ttf",
                                        override val tilesetType: TilesetType = TilesetType.TRUE_TYPE_FONT,
                                        override val id: Identifier = Identifier.randomIdentifier(),
                                        override val tileType: TileType = TileType.CHARACTER_TILE,
                                        override val path: String = "/monospace_fonts/$fileName")
    : TilesetResource {

    /**
     * These fonts are taken from Google Fonts.
     */
    UBUNTU_MONO_6X12("ubuntu_mono", 6, 12),
    UBUNTU_MONO_8X16("ubuntu_mono", 8, 16),
    UBUNTU_MONO_9X18("ubuntu_mono", 9, 18),
    UBUNTU_MONO_10X20("ubuntu_mono", 10, 20),
    INCONSOLATA_6X12("inconsolata", 6, 12),
    INCONSOLATA_8X16("inconsolata", 8, 16),
    INCONSOLATA_9X18("inconsolata", 9, 18),
    INCONSOLATA_10X20("inconsolata", 10, 20),
    /**
     * These fonts are part of the Ultimate Oldschool PC Font Pack and licensed under
     * Creative Commons Attribution-ShareAlike 4.0 International License.
     * See: https://int10h.org/oldschool-pc-fonts/readme/
     */
    AMSTRAD_12X12("amstrad", 12, 12),
    AMSTRAD_16X16("amstrad", 16, 16),
    AMSTRAD_18X18("amstrad", 18, 18),
    AMSTRAD_20X20("amstrad", 20, 20),
    AMSTRAD_6X12("amstrad_2y", 6, 12),
    AMSTRAD_8X16("amstrad_2y", 8, 16),
    AMSTRAD_9X18("amstrad_2y", 9, 18),
    AMSTRAD_10X20("amstrad_2y", 10, 20),

    IBM_BIOS_12X12("ibm_bios", 12, 12),
    IBM_BIOS_16X16("ibm_bios", 16, 16),
    IBM_BIOS_18X18("ibm_bios", 18, 18),
    IBM_BIOS_20X20("ibm_bios", 20, 20),
    IBM_BIOS_6X12("ibm_bios_2y", 6, 12),
    IBM_BIOS_8X16("ibm_bios_2y", 8, 16),
    IBM_BIOS_9X18("ibm_bios_2y", 9, 18),
    IBM_BIOS_10X20("ibm_bios_2y", 10, 20),
    IBM_BIOS_12X6("ibm_bios_2x", 12, 6),
    IBM_BIOS_16X8("ibm_bios_2x", 16, 8),
    IBM_BIOS_18X9("ibm_bios_2x", 18, 9),
    IBM_BIOS_20X10("ibm_bios_2x", 20, 10),

    
    TEST("PxPlus_IBM_BIOS-2y", 10, 20);

}
