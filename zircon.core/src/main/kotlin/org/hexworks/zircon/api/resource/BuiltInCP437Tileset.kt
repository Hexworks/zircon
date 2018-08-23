package org.hexworks.zircon.api.resource

import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.util.Identifier
import kotlin.reflect.KClass

/**
 * This enum encapsulates the means of loading CP437 tilesets.
 * You can either use a built-in tileset (extracted from the Dwarf Fortress tileset
 * repository) or you can load your own using TODO
 */
enum class BuiltInCP437Tileset(private val tilesetName: String,
                               override val width: Int,
                               override val height: Int,
                               private val fileName: String = "${tilesetName}_${width}x$height.png",
                               override val id: Identifier = Identifier.randomIdentifier(),
                               override val tileType: KClass<CharacterTile> = CharacterTile::class,
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
    REX_PAINT_20X20("rex_paint", 20, 20);

}
