package org.hexworks.zircon.internal.data

import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.BlockSide
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.base.BlockBase
import org.hexworks.zircon.platform.extension.getOrDefault

data class DefaultBlock<T : Tile>(
        override val layers: MutableList<T>,
        private val sides: MutableMap<BlockSide, T>,
        private val emptyTile: T) : BlockBase<T>() {

    override fun createCopy(): Block<T> {
        return copy(layers = layers.toMutableList(),
                sides = sides.toMutableMap())
    }

    override fun fetchSide(side: BlockSide): T {
        return sides.getOrDefault(side, emptyTile)
    }

    override fun setSide(side: BlockSide, tile: T) {
        sides[side] = tile
    }

}
