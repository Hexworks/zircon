package org.hexworks.zircon.api.resource

import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.internal.behavior.Identifiable

/**
 * Contains metadata about a tileset for a given [Tile] type.
 */
interface TilesetResource : Identifiable {

    val tileType: TileType
    val tilesetType: TilesetType
    val width: Int
    val height: Int
    val path: String

    val size: Size
        get() = Size.create(width, height)

    fun isCompatibleWith(other: TilesetResource): Boolean {
        return other.tileType == tileType &&
                other.width == width &&
                other.height == height
    }
}
