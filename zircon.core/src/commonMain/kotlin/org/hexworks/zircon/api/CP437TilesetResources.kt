package org.hexworks.zircon.api

import org.hexworks.zircon.internal.resource.BuiltInCP437TilesetResource.*
import org.hexworks.zircon.internal.resource.CP437TilesetResource
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.resource.TilesetSourceType
import kotlin.jvm.JvmStatic

/**
 * This object can be used to load either built-in CP437 [TilesetResource]s
 * or external ones.
 */
object CP437TilesetResources {

    // These tilesets are from the Dwarf Fortress Tileset Repository:
    // http://dwarffortresswiki.org/Tileset_repository
    @JvmStatic
    fun acorn8X16(): TilesetResource = ACORN_8X16

    @JvmStatic
    fun aduDhabi16x16(): TilesetResource = ADU_DHABI_16X16

    @JvmStatic
    fun aesomatica16x16(): TilesetResource = AESOMATICA_16X16

    @JvmStatic
    fun anikki16x16(): TilesetResource = ANIKKI_16X16

    @JvmStatic
    fun anikki20x20(): TilesetResource = ANIKKI_20X20

    @JvmStatic
    fun bisasam16x16(): TilesetResource = BISASAM_16X16

    @JvmStatic
    fun bisasam20x20(): TilesetResource = BISASAM_20X20

    @JvmStatic
    fun bisasam24x24(): TilesetResource = BISASAM_24X24

    @JvmStatic
    fun cheepicus14x14(): TilesetResource = CHEEPICUS_14X14

    @JvmStatic
    fun cheepicus15x15(): TilesetResource = CHEEPICUS_15X15

    @JvmStatic
    fun cheepicus16x16(): TilesetResource = CHEEPICUS_16X16

    @JvmStatic
    fun cla18x18(): TilesetResource = CLA_18X18

    @JvmStatic
    fun cooz16x16(): TilesetResource = COOZ_16X16

    @JvmStatic
    fun curses24x24(): TilesetResource = CURSES_24X24

    @JvmStatic
    fun fnord16x16(): TilesetResource = FNORD_16X16

    @JvmStatic
    fun guybrush16x16(): TilesetResource = GUYBRUSH_16X16

    @JvmStatic
    fun hack64x64(): TilesetResource = HACK_64X64

    @JvmStatic
    fun haowan18x18(): TilesetResource = HAOWAN_18X18

    @JvmStatic
    fun jackard16x16(): TilesetResource = JACKARD_16X16

    @JvmStatic
    fun jolly12x12(): TilesetResource = JOLLY_12X12

    @JvmStatic
    fun kelora16x16(): TilesetResource = KELORA_16X16

    @JvmStatic
    fun kenran16x16(): TilesetResource = KENRAN_16X16

    @JvmStatic
    fun lordNightmare8X16(): TilesetResource = LORD_NIGHTMARE_8X16

    @JvmStatic
    fun markX16x16(): TilesetResource = MARK_X_16X16

    @JvmStatic
    fun mdCurses16x16(): TilesetResource = MD_CURSES_16X16

    @JvmStatic
    fun mkv12x12(): TilesetResource = MKV_12X12

    @JvmStatic
    fun msGothic16x16(): TilesetResource = MS_GOTHIC_16X16

    @JvmStatic
    fun nagidal16x16(): TilesetResource = NAGIDAL_16X16

    @JvmStatic
    fun nagidal24x24(): TilesetResource = NAGIDAL_24X24

    @JvmStatic
    fun nordic16x16(): TilesetResource = NORDIC_16X16

    @JvmStatic
    fun obsidian16x16(): TilesetResource = OBSIDIAN_16X16

    @JvmStatic
    fun oreslam20x20(): TilesetResource = ORESLAM_20X20

    @JvmStatic
    fun phoebus16x16(): TilesetResource = PHOEBUS_16X16

    @JvmStatic
    fun raving16x16(): TilesetResource = RAVING_16X16

    @JvmStatic
    fun rogueYun16x16(): TilesetResource = ROGUE_YUN_16X16

    @JvmStatic
    fun runeset16x16(): TilesetResource = RUNESET_16X16

    @JvmStatic
    fun runeset24x24(): TilesetResource = RUNESET_24X24

    @JvmStatic
    fun runeset32x32(): TilesetResource = RUNESET_32X32

    @JvmStatic
    fun sb16x16(): TilesetResource = SB_16X16

    @JvmStatic
    fun sirHenry32x32(): TilesetResource = SIR_HENRY_32X32

    @JvmStatic
    fun taffer20x20(): TilesetResource = TAFFER_20X20

    @JvmStatic
    fun talryth15x15(): TilesetResource = TALRYTH_15X15

    @JvmStatic
    fun teeto18x18(): TilesetResource = TEETO_18X18

    @JvmStatic
    fun tyr16x16(): TilesetResource = TYR_16X16

    @JvmStatic
    fun vga8X16(): TilesetResource = VGA_8X16

    @JvmStatic
    fun vidumec12x12(): TilesetResource = VIDUMEC_12X12

    @JvmStatic
    fun wanderlust16x16(): TilesetResource = WANDERLUST_16X16

    @JvmStatic
    fun yayo12x12(): TilesetResource = YAYO_12X12

    @JvmStatic
    fun yayo16x16(): TilesetResource = YAYO_16X16

    @JvmStatic
    fun yobbo20x20(): TilesetResource = YOBBO_20X20

    @JvmStatic
    fun zaratustra16x16(): TilesetResource = ZARATUSTRA_16X16

    @JvmStatic
    fun zilk16x16(): TilesetResource = ZILK_16X16

    // These tilesets are from REXPaint, a great ASCII art editor
    // https://www.gridsagegames.com/rexpaint/
    // Huge thanks to Kyzrati who let us bundle these into Zircon
    @JvmStatic
    fun rexPaint8x8(): TilesetResource = REX_PAINT_8X8

    @JvmStatic
    fun rexPaint10x10(): TilesetResource = REX_PAINT_10X10

    @JvmStatic
    fun rexPaint12x12(): TilesetResource = REX_PAINT_12X12

    @JvmStatic
    fun rexPaint14x14(): TilesetResource = REX_PAINT_14X14

    @JvmStatic
    fun rexPaint16x16(): TilesetResource = REX_PAINT_16X16

    @JvmStatic
    fun rexPaint18x18(): TilesetResource = REX_PAINT_18X18

    @JvmStatic
    fun rexPaint20x20(): TilesetResource = REX_PAINT_20X20

    /**
     * Use this function if you want to load a [TilesetResource]
     * from the filesystem.
     */
    @JvmStatic
    fun loadTilesetFromFilesystem(
            width: Int,
            height: Int,
            path: String): TilesetResource {
        return CP437TilesetResource(
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
        return CP437TilesetResource(
                width = width,
                height = height,
                path = path,
                tilesetSourceType = TilesetSourceType.JAR)
    }
}
