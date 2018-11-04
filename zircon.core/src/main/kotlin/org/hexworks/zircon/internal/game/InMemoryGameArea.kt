package org.hexworks.zircon.internal.game

import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.impl.Position3D
import org.hexworks.zircon.api.data.impl.Size3D
import org.hexworks.zircon.api.game.GameArea
import org.hexworks.zircon.api.util.Maybe
import org.hexworks.zircon.internal.util.TreeMap
import org.hexworks.zircon.platform.factory.TreeMapFactory

class InMemoryGameArea<T: Tile, B : Block<T>>(
        override val defaultBlock: B,
        override val size: Size3D,
        private val layersPerBlock: Int) : GameArea<T, B> {

    private val blocks: TreeMap<Position3D, B> = TreeMapFactory.create()

    override fun layersPerBlock() = layersPerBlock

    override fun hasBlockAt(position: Position3D): Boolean {
        return blocks.containsKey(position)
    }

    override fun fetchBlockAt(position: Position3D): Maybe<B> {
        return Maybe.ofNullable(blocks[position])
    }

    @Suppress("UNCHECKED_CAST")
    override fun fetchBlockOrDefault(position: Position3D) =
            blocks.getOrDefault(position, defaultBlock.withPosition(position) as B)

    override fun fetchBlocks(): Iterable<B> {
        return blocks.values.toList()
    }

    override fun setBlockAt(position: Position3D, block: B) {
        require(size.containsPosition(position)) {
            "The supplied position ($position) is not within the size ($size) of this game area."
        }
        val layerCount = block.layers.size
        require(layerCount == layersPerBlock) {
            "The number of layers per block for this game area is $layersPerBlock." +
                    " The supplied layers have a size of $layerCount."
        }
        blocks[position] = block
    }
}
