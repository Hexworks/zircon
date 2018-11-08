package org.hexworks.zircon.api.resource

import org.hexworks.cobalt.datatypes.Identifier
import org.hexworks.cobalt.datatypes.factory.IdentifierFactory

/**
 * This enum contains the metadata for the built-in CP437 tilesets.
 */
enum class BuiltInCP437TilesetResource(val tilesetName: String,
                                       override val width: Int,
                                       override val height: Int,
                                       val fileName: String = "${tilesetName}_${width}x$height.png",
                                       override val id: Identifier = IdentifierFactory.randomIdentifier(),
                                       override val tileType: TileType = TileType.CHARACTER_TILE,
                                       override val tilesetType: TilesetType = TilesetType.CP437_TILESET,
                                       override val path: String = "/cp_437_tilesets/$fileName")
    : TilesetResource {

    JOLLY_12X12("jolly", 12, 12),
    ADU_DHABI_16X16("adu_dhabi", 16, 16),
    AESOMATICA_16X16("aesomatica", 16, 16),
    BISASAM_16X16("bisasam", 16, 16),
    CHEEPICUS_16X16("cheepicus", 16, 16),
    OBSIDIAN_16X16("obsidian", 16, 16),
    PHOEBUS_16X16("phoebus", 16, 16),
    ROGUE_YUN_16X16("rogue_yun", 16, 16),
    WANDERLUST_16X16("wanderlust", 16, 16),
    CLA_18X18("cla", 18, 18),
    BISASAM_20X20("bisasam", 20, 20),
    TAFFER_20X20("taffer", 20, 20),
    YOBBO_20X20("yobbo", 20, 20),
    REX_PAINT_8X8("rex_paint", 8, 8),
    REX_PAINT_10X10("rex_paint", 10, 10),
    REX_PAINT_12X12("rex_paint", 12, 12),
    REX_PAINT_14X14("rex_paint", 14, 14),
    REX_PAINT_16X16("rex_paint", 16, 16),
    REX_PAINT_18X18("rex_paint", 18, 18),
    REX_PAINT_20X20("rex_paint", 20, 20),
    ACORN_8X16("acorn", 8, 16),
    LORD_NIGHTMARE_8X16("lord_nightmare", 8, 16),
    VGA_8X16("vga", 8, 16);

}
