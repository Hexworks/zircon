package org.hexworks.zircon.internal.resource

import org.hexworks.cobalt.core.api.UUID
import org.hexworks.zircon.api.behavior.HasTitle
import org.hexworks.zircon.api.data.TileType
import org.hexworks.zircon.api.resource.ResourceType
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.resource.TilesetType

/**
 * This enum contains the metadata for the built-in CP437 tilesets.
 */
@Suppress("SpellCheckingInspection")
public enum class BuiltInCP437TilesetResource(
    private val tilesetName: String,
    override val title: String,
    override val width: Int,
    override val height: Int,
    private val fileName: String = "${tilesetName}_${width}x$height.png",
    override val id: UUID = UUID.randomUUID(),
    override val tileType: TileType = TileType.CHARACTER_TILE,
    override val tilesetType: TilesetType = TilesetType.CP437Tileset,
    override val path: String = "/cp_437_tilesets/$fileName",
    override val resourceType: ResourceType = ResourceType.PROJECT
) : TilesetResource, HasTitle {

    // These fonts come from the Dwarf Fortress Tileset Repository
    ACORN_8X16("acorn", "Acorn 8x16", 8, 16),
    ADU_DHABI_16X16("adu_dhabi", "Adu Dhabi 16x16", 16, 16),
    ANIKKI_16X16("anikki", "Anikki 16x16", 16, 16),
    ANIKKI_20X20("anikki", "Anikki 20x20", 20, 20),
    BISASAM_16X16("bisasam", "Bisasam 16x16", 16, 16),
    BISASAM_20X20("bisasam", "Bisasam 20x20", 20, 20),
    BISASAM_24X24("bisasam", "Bisasam 24x24", 24, 24),
    COOZ_16X16("cooz", "Cooz 16x16", 16, 16),
    CURSES_24X24("curses", "Curses 24x24", 24, 24),
    GUYBRUSH_16X16("guybrush", "Guybrush 16x16", 16, 16),
    HACK_64X64("hack", "Hack 64x64", 64, 64),
    HAOWAN_18X18("haowan", "Haowan 18x18", 18, 18),
    KENRAN_16X16("kenran", "Kenran 16x16", 16, 16),
    LORD_NIGHTMARE_8X16("lord_nightmare", "Lord Nightmare 8x16", 8, 16),
    MARK_X_16X16("mark_x", "Mark X 16x16", 16, 16),
    MKV_12X12("mkv", "MKV 12x12", 12, 12),
    NAGIDAL_24X24("nagidal", "Nagidal 24x24", 24, 24),
    RAVING_16X16("raving", "Raving 16x16", 16, 16),
    ROGUE_YUN_16X16("rogue_yun", "Rogue Yun 16x16", 16, 16),
    RUNESET_32X32("runeset", "Runeset 32x32", 32, 32),
    SB_16X16("sb", "SB 16x16", 16, 16),
    SIR_HENRY_32X32("sir_henry", "Sir Henry 32x32", 32, 32),
    TAFFER_20X20("taffer", "Taffer 20x20", 20, 20),
    TALRYTH_15X15("talryth", "Talryth 15x15", 15, 15),
    TYR_16X16("tyr", "Tyr 16x16", 16, 16),
    VGA_8X16("vga", "VGA 8x16", 8, 16),
    VIDUMEC_12X12("vidumec", "Vidumec 12x12", 12, 12),
    WANDERLUST_16X16("wanderlust", "Wanderlust 16x16", 16, 16),
    YAYO_16X16("yayo", "Yayo 16x16", 16, 16),
    YOBBO_20X20("yobbo", "Yobbo 20x20", 20, 20),
    ZARATUSTRA_16X16("zaratustra", "Zaratustra 16x16", 16, 16),
    ZILK_16X16("zilk", "Zilk 16x16", 16, 16),

    // These tilesets come from REXPaint
    REX_PAINT_8X8("rex_paint", "REXPaint 8x8", 8, 8),
    REX_PAINT_10X10("rex_paint", "REXPaint 10x10", 10, 10),
    REX_PAINT_12X12("rex_paint", "REXPaint 12x12", 12, 12),
    REX_PAINT_14X14("rex_paint", "REXPaint 14x14", 14, 14),
    REX_PAINT_16X16("rex_paint", "REXPaint 16x16", 16, 16),
    REX_PAINT_18X18("rex_paint", "REXPaint 18x18", 18, 18),
    REX_PAINT_20X20("rex_paint", "REXPaint 20x20", 20, 20);

}
