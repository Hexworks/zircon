package org.hexworks.zircon.internal.data

import kotlinx.collections.immutable.PersistentMap
import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.BlockTileType
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.base.BaseBlock

class DefaultBlock<T : Tile> internal constructor(
    emptyTile: T,
    initialTiles: Map<BlockTileType, T>
) : BaseBlock<T>(emptyTile, initialTiles.toMutableMap()) {

    override fun createCopy(): Block<T> {
        return DefaultBlock(
            emptyTile = emptyTile,
            initialTiles = tiles
        )
    }
}
