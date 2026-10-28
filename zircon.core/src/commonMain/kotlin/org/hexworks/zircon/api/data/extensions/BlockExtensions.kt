package org.hexworks.zircon.api.data.extensions

import org.hexworks.zircon.api.builder.data.BlockBuilder
import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.BlockTileType
import org.hexworks.zircon.api.data.BlockTileType.BACK
import org.hexworks.zircon.api.data.BlockTileType.BOTTOM
import org.hexworks.zircon.api.data.BlockTileType.CONTENT
import org.hexworks.zircon.api.data.BlockTileType.FRONT
import org.hexworks.zircon.api.data.BlockTileType.LEFT
import org.hexworks.zircon.api.data.BlockTileType.RIGHT
import org.hexworks.zircon.api.data.BlockTileType.TOP
import org.hexworks.zircon.api.data.Tile
import kotlin.collections.set

public var <T: Tile> Block<T>.top: T
    get() = tiles[TOP] ?: emptyTile
    set(value) {
        tiles[TOP] = value
    }

public var <T: Tile> Block<T>.bottom: T
    get() = tiles[BOTTOM] ?: emptyTile
    set(value) {
        tiles[BOTTOM] = value
    }

public var <T: Tile> Block<T>.front: T
    get() = tiles[FRONT] ?: emptyTile
    set(value) {
        tiles[FRONT] = value
    }

public var <T: Tile> Block<T>.back: T
    get() = tiles[BACK] ?: emptyTile
    set(value) {
        tiles[BACK] = value
    }

public var <T: Tile> Block<T>.left: T
    get() = tiles[LEFT] ?: emptyTile
    set(value) {
        tiles[LEFT] = value
    }

public var <T: Tile> Block<T>.right: T
    get() = tiles[RIGHT] ?: emptyTile
    set(value) {
        tiles[RIGHT] = value
    }

public var <T: Tile> Block<T>.content: T
    get() = tiles[CONTENT] ?: emptyTile
    set(value) {
        tiles[CONTENT] = value
    }


/**
 * Tells whether this [Block] is empty (all of its sides and content are
 * the [emptyTile]).
 */
public fun <T: Tile> Block<T>.isEmpty(): Boolean = tiles.isEmpty() || tiles.values.all { it == emptyTile }

/**
 * Returns a new [Block] which is a rotation of this [Block]
 * around the *x* axis.
 */
public fun <T: Tile> Block<T>.withFlippedAroundX(): Block<T> {
    return createCopy().apply {
        val temp = right
        this.right = left
        left = temp
    }
}

/**
 * Returns a new [Block] which is a rotation of this [Block]
 * around the *y* axis.
 */
public fun <T: Tile> Block<T>.withFlippedAroundY(): Block<T> {
    return createCopy().apply {
        val temp = front
        this.front = back
        back = temp
    }
}

/**
 * Returns a new [Block] which is a rotation of this [Block]
 * around the *z* axis.
 */
public fun <T: Tile> Block<T>.withFlippedAroundZ(): Block<T> {
    return createCopy().apply {
        val temp = top
        this.top = bottom
        bottom = temp
    }
}

/**
 * Returns the tile from this [Block] for the given [blockTileType].
 */
public fun <T: Tile> Block<T>.getTileByType(blockTileType: BlockTileType): T {
    return tiles[blockTileType] ?: emptyTile
}

/**
 * Creates a new [BlockBuilder] preconfigured with the contents of
 * this [Block].
 */
public fun <T: Tile> Block<T>.toBuilder(): BlockBuilder<T> = BlockBuilder<T>().apply {
    emptyTile = this@toBuilder.emptyTile
    tiles = this@toBuilder.tiles
}
