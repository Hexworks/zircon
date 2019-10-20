package org.hexworks.zircon.api.data

/**
 * Represents the types of [Tile]s a [Block]
 * can have.
 */
enum class BlockTileType(
        /**
         * The ordering of the sides. Only necessary for internal functions
         * when storing the actual tiles. We're not using `ordinal` because
         * it can be changed accidentally when the enum values are reordered.
         */
        internal val order: Int) {
    TOP(0),
    BOTTOM(1),
    LEFT(2),
    RIGHT(3),
    FRONT(4),
    BACK(5),
    CONTENT(6)
}
