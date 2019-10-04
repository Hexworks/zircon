package org.hexworks.zircon.api.resource

import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.internal.behavior.Identifiable
import org.hexworks.zircon.internal.resource.TileType
import org.hexworks.zircon.internal.resource.TilesetSourceType
import org.hexworks.zircon.internal.resource.TilesetType

/**
 * Contains metadata about a tileset for a given [Tile] type.
 */
interface TilesetResource : Identifiable {

    val tileType: TileType
    val tilesetType: TilesetType
    val tilesetSourceType: TilesetSourceType
    val width: Int
    val height: Int
    val path: String

    val size: Size
        get() = Size.create(width, height)

    /**
     * A [TilesetResource] is compatible with another if they have
     * the same size. TODO: why did we check for [tileType]?
     */
    fun isCompatibleWith(other: TilesetResource): Boolean {
        return other.width == width &&
                other.height == height
    }

    fun checkCompatibilityWith(other: TilesetResource) {
        require(isCompatibleWith(other)) {
            "The supplied tileset: $other is not compatible with this: $this."
        }
    }
}
