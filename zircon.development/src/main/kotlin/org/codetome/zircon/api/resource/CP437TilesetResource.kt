package org.codetome.zircon.api.resource

import org.codetome.zircon.api.tileset.Tileset

/**
 * This enum encapsulates the means of loading CP437 tilesets.
 * You can either use a built-in tileset (extracted from the Dwarf Fortress tileset
 * repository) or you can load your own using [CP437TilesetResource.loadCP437Tileset]
 */
enum class CP437TilesetResource(private val tilesetName: String,
                                val width: Int,
                                val height: Int,
                                private val fileName: String = "${tilesetName}_${width}x$height.png",
                                val path: String = "zircon.core/src/main/resources/cp_437_tilesets/$fileName") {

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
    REX_PAINT_20X20("rex_paint", 20, 20);

    /**
     * Loads this built-in tileset as a tiled [Tileset].
     */
    fun <T: Any, S : Any> toFont(): Tileset<T, S> {
        return toFont(true)
    }

    /**
     * Loads this built-in tileset as a tiled [Tileset].
     */
    fun <T: Any, S : Any> toFont(cacheFonts: Boolean): Tileset<T, S> {
        return loadCP437Tileset(
                width = width,
                height = height,
                path = path,
                cacheFonts = cacheFonts)
    }

    companion object {

        /**
         * Loads a tileset from the given `source` as a tiled [Tileset].
         * *Note that* it is your responsibility to supply the proper parameters for
         * this method!
         */
        fun <T: Any, S : Any> loadCP437Tileset(width: Int,
                             height: Int,
                             path: String,
                             cacheFonts: Boolean = true): Tileset<T, S> {
            TODO()
        }
    }
}
