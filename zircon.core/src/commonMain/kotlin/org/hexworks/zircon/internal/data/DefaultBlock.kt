package org.hexworks.zircon.internal.data

import org.hexworks.zircon.api.data.BlockTileType
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.base.BaseBlock

class DefaultBlock<T : Tile>(
        emptyTile: T,
        initialTiles: Map<BlockTileType, T>) : BaseBlock<T>(emptyTile, initialTiles)