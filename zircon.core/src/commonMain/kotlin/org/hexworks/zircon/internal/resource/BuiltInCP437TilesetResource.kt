package org.hexworks.zircon.internal.resource

import org.hexworks.cobalt.Identifier
import org.hexworks.cobalt.factory.IdentifierFactory
import org.hexworks.zircon.api.resource.TilesetResource

/**
 * This enum contains the metadata for the built-in CP437 tilesets.
 */
internal enum class BuiltInCP437TilesetResource(
        val tilesetName: String,
        override val width: Int,
        override val height: Int,
        val fileName: String = "${tilesetName}_${width}x$height.png",
        override val id: Identifier = IdentifierFactory.randomIdentifier(),
        override val tileType: TileType = TileType.CHARACTER_TILE,
        override val tilesetType: TilesetType = TilesetType.CP437_TILESET,
        override val path: String = "/cp_437_tilesets/$fileName",
        override val tilesetSourceType: TilesetSourceType = TilesetSourceType.JAR)
    : TilesetResource {

    // These fonts come from the Dwarf Fortress Tileset Repository
    ACORN_8X16("acorn", 8, 16),
    ADU_DHABI_16X16("adu_dhabi", 16, 16),
    AESOMATICA_16X16("aesomatica", 16, 16),
    ANIKKI_16X16("anikki", 16, 16),
    ANIKKI_20X20("anikki", 20, 20),
    BISASAM_16X16("bisasam", 16, 16),
    BISASAM_20X20("bisasam", 20, 20),
    BISASAM_24X24("bisasam", 24, 24),
    CHEEPICUS_14X14("cheepicus", 14, 14),
    CHEEPICUS_15X15("cheepicus", 15, 15),
    CHEEPICUS_16X16("cheepicus", 16, 16),
    CLA_18X18("cla", 18, 18),
    COOZ_16X16("cooz", 16, 16),
    CURSES_24X24("curses", 24, 24),
    FNORD_16X16("fnord", 16, 16),
    GUYBRUSH_16X16("guybrush", 16, 16),
    HACK_64X64("hack", 64, 64),
    HAOWAN_18X18("haowan", 18, 18),
    JACKARD_16X16("jackard", 16, 16),
    JOLLY_12X12("jolly", 12, 12),
    KELORA_16X16("kelora", 16, 16),
    KENRAN_16X16("kenran", 16, 16),
    LORD_NIGHTMARE_8X16("lord_nightmare", 8, 16),
    MARK_X_16X16("mark_x", 16, 16),
    MD_CURSES_16X16("md_curses", 16, 16),
    MKV_12X12("mkv", 12, 12),
    MS_GOTHIC_16X16("ms_gothic", 16, 16),
    NAGIDAL_16X16("nagidal", 16, 16),
    NAGIDAL_24X24("nagidal", 24, 24),
    NORDIC_16X16("nordic", 16, 16),
    OBSIDIAN_16X16("obsidian", 16, 16),
    ORESLAM_20X20("oreslam", 20, 20),
    PHOEBUS_16X16("phoebus", 16, 16),
    RAVING_16X16("raving", 16, 16),
    ROGUE_YUN_16X16("rogue_yun", 16, 16),
    RUNESET_16X16("runeset", 16, 16),
    RUNESET_24X24("runeset", 24, 24),
    RUNESET_32X32("runeset", 32, 32),
    SB_16X16("sb", 16, 16),
    SIR_HENRY_32X32("sir_henry", 32, 32),
    TAFFER_20X20("taffer", 20, 20),
    TALRYTH_15X15("talryth", 15, 15),
    TEETO_18X18("teeto", 18, 18),
    TYR_16X16("tyr", 16, 16),
    VGA_8X16("vga", 8, 16),
    VIDUMEC_12X12("vidumec", 12, 12),
    WANDERLUST_16X16("wanderlust", 16, 16),
    YAYO_12X12("yayo", 12, 12),
    YAYO_16X16("yayo", 16, 16),
    YOBBO_20X20("yobbo", 20, 20),
    ZARATUSTRA_16X16("zaratustra", 16, 16),
    ZILK_16X16("zilk", 16, 16),

    // These tilesets come from REXPaint
    REX_PAINT_8X8("rex_paint", 8, 8),
    REX_PAINT_10X10("rex_paint", 10, 10),
    REX_PAINT_12X12("rex_paint", 12, 12),
    REX_PAINT_14X14("rex_paint", 14, 14),
    REX_PAINT_16X16("rex_paint", 16, 16),
    REX_PAINT_18X18("rex_paint", 18, 18),
    REX_PAINT_20X20("rex_paint", 20, 20);

}
