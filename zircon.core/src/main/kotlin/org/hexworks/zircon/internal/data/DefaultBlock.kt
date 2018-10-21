package org.hexworks.zircon.internal.data

import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.BlockSide
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.base.BlockBase
import org.hexworks.zircon.api.data.impl.Position3D
import org.hexworks.zircon.platform.extension.getOrDefault

data class DefaultBlock<T : Tile>(
        override val position: Position3D,
        override val layers: MutableList<T>,
        private val sides: Map<BlockSide, T>,
        private val emptyTile: T) : BlockBase<T>() {

    override fun withPosition(position: Position3D): Block<T> {
        return copy(position = position,
                layers = layers.toMutableList(),
                sides = sides.toMutableMap())
    }

    override fun fetchSide(side: BlockSide): T {
        return sides.getOrDefault(side, emptyTile)
    }

}
