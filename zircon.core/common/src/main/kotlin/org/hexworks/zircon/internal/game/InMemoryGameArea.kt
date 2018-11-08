package org.hexworks.zircon.internal.game

import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.impl.Position3D
import org.hexworks.zircon.api.data.impl.Size3D
import org.hexworks.zircon.api.game.Cell3D
import org.hexworks.zircon.api.game.base.BaseGameArea
import org.hexworks.zircon.api.util.Maybe
import org.hexworks.zircon.internal.util.TreeMap
import org.hexworks.zircon.platform.factory.TreeMapFactory

class InMemoryGameArea<T : Tile, B : Block<T>>(
        visibleSize: Size3D,
        actualSize: Size3D,
        override val defaultBlock: B,
        private val layersPerBlock: Int) : BaseGameArea<T, B>(
        visibleSize = visibleSize,
        actualSize = actualSize) {

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
            blocks.getOrDefault(position, defaultBlock)

    override fun fetchBlocks(): Iterable<Cell3D<T, B>> {
        return blocks.keys.map { createCell(it) }
    }

    override fun setBlockAt(position: Position3D, block: B) {
        require(actualSize().containsPosition(position)) {
            "The supplied position ($position) is not within the size (${actualSize()}) of this game area."
        }
        val layerCount = block.layers.size
        require(layerCount == layersPerBlock) {
            "The number of layers per block for this game area is $layersPerBlock." +
                    " The supplied layers have a size of $layerCount."
        }
        blocks[position] = block
    }
}
