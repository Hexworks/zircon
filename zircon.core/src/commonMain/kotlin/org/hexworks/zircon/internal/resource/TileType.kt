package org.hexworks.zircon.internal.resource

import org.hexworks.zircon.api.data.Tile

/**
 * Contains the possible types of [Tile]s.
 */
enum class TileType {
    /**
     * Represents a [Tile] that contains a character (like an `x`).
     */
    CHARACTER_TILE,
    /**
     * Represents a [Tile] that contains some graphics.
     */
    GRAPHIC_TILE,
    /**
     * Represents a [Tile] that contains some an image. There is an important
     */
    IMAGE_TILE
}
