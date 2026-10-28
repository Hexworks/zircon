package org.hexworks.zircon.api.tileset

import kotlinx.serialization.Serializable

/**
 * Describes a graphical tileset: a zip archive holding this document as `tileset.json`
 * next to one or more sprite sheet images.
 */
@Serializable
public data class GraphicalTilesetMetadata(
    val name: String,
    /** Width and height of one tile in pixels; tiles are square. */
    val tileSize: Int,
    val files: List<GraphicalTilesetFile>,
)

/**
 * One sprite sheet inside a graphical tileset. Tiles are read left to right, top to
 * bottom; [tiles] lists them in that order.
 */
@Serializable
public data class GraphicalTilesetFile(
    /** Path of the image inside the archive, for example `tiles.png`. */
    val name: String,
    val tilesPerRow: Int,
    val tiles: List<GraphicalTileInfo>,
)

/**
 * One tile of a sprite sheet. [tags] defaults to the unique lowercase words of [name],
 * [char] to the first character of [name].
 */
@Serializable
public data class GraphicalTileInfo(
    val name: String,
    val tags: Set<String> = name.lowercase().split(' ').filter { it.isNotBlank() }.toSet(),
    val char: String = name.take(1),
    val description: String = "",
)
