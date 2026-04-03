package org.hexworks.zircon.internal.data

import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.BlockTileType
import org.hexworks.zircon.api.data.Tile

class DefaultBlock<T : Tile> internal constructor(
    override val emptyTile: T,
    override val tiles: MutableMap<BlockTileType, T>
) : Block<T> {

    override fun createCopy(): Block<T> {
        return DefaultBlock(
            emptyTile = emptyTile,
            tiles = tiles
        )
    }
}
