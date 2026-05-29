package org.hexworks.zircon.api.data

import org.hexworks.zircon.api.behavior.Copiable

/**
 * A [Block] is a voxel that consists of [Tile]s representing each side, and the internal
 * content of the [Block]. All sides of the [Block] are optional. If any of them are missing
 * the [emptyTile] will be used when the sides are dereferenced.
 * If you want to have multiple [Tile]s in any side (or as the content) try using a [StackedTile].
 */

interface Block<T : Tile> : Copiable<Block<T>> {
    val emptyTile: T
    val tiles: MutableMap<BlockTileType, T>
}
