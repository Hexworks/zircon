package org.hexworks.zircon.internal.data

import org.hexworks.zircon.api.data.BlockSide
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.base.BlockBase
import org.hexworks.zircon.platform.extension.getOrDefault

data class DefaultBlock<T : Tile>(
        override val layers: MutableList<T>,
        private val sides: Map<BlockSide, T>,
        private val emptyTile: T) : BlockBase<T>() {

    override fun fetchSide(side: BlockSide): T {
        return sides.getOrDefault(side, emptyTile)
    }

}
