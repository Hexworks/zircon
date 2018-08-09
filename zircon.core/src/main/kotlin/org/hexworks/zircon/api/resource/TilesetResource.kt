package org.hexworks.zircon.api.resource

import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.internal.behavior.Identifiable
import kotlin.reflect.KClass

/**
 * Contains metadata about a tileset for a given [Tile] type.
 */
interface TilesetResource : Identifiable {

    val tileType: KClass<out Tile>
    val width: Int
    val height: Int
    val path: String

    fun size() = Size.create(width, height)

    fun isCompatibleWith(other: TilesetResource): Boolean {
        return other.tileType == tileType &&
                other.width == width &&
                other.height == height
    }
}
