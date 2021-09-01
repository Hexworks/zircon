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
     * Represents a [Tile] that contains graphics.
     * The difference between [GRAPHICAL_TILE] and [IMAGE_TILE] is that
     * - [GRAPHICAL_TILE]s are part of a tileset
     * - [IMAGE_TILE]s contain images loaded individually from a path
     */
    GRAPHICAL_TILE,

    /**
     * Represents a [Tile] that contains some an image.
     * The difference between [GRAPHICAL_TILE] and [IMAGE_TILE] is that
     * - [GRAPHICAL_TILE]s are part of a tileset
     * - [IMAGE_TILE]s contain images loaded individually from a path
     */
    IMAGE_TILE;

    companion object
}
