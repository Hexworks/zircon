package org.hexworks.zircon.internal.game

import org.hexworks.zircon.api.builder.data.BlockBuilder
import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.impl.Position3D
import org.hexworks.zircon.api.data.impl.Size3D
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.game.GameArea
import org.hexworks.zircon.api.util.Maybe
import org.hexworks.zircon.internal.util.TreeMap
import org.hexworks.zircon.platform.factory.TreeMapFactory

class InMemoryGameArea(override val size: Size3D,
                       private val layersPerBlock: Int,
                       private val filler: Tile = Tile.empty()) : GameArea {

    private val emptyBlockLayers = (0 until layersPerBlock).map { filler }
    private val blocks: TreeMap<Position3D, Block> = TreeMapFactory.create()

    override fun layersPerBlock() = layersPerBlock

    override fun hasBlockAt(position: Position3D): Boolean {
        return blocks.containsKey(position)
    }

    override fun fetchBlockAt(position: Position3D): Maybe<Block> {
        return Maybe.ofNullable(blocks[position])
    }

    override fun fetchBlockOrDefault(position: Position3D) =
            blocks.getOrDefault(position,
                    BlockBuilder.create()
                            .withPosition(position)
                            .withLayers(emptyBlockLayers.toMutableList())
                            .build())

    override fun fetchBlocks(): Iterable<Block> {
        return blocks.values.toList()
    }

    override fun setBlockAt(position: Position3D, block: Block) {
        require(size.containsPosition(position)) {
            "The supplied position ($position) is not within the size ($size) of this game area."
        }
        val layerCount = block.layers.size
        require(layerCount <= layersPerBlock) {
            "The number of layers per block for this game area is $layersPerBlock." +
                    " The supplied layers have a size of $layerCount."
        }
        if (layerCount < layersPerBlock) {
            (layerCount until layersPerBlock).forEach {
                block.layers.add(Tile.empty())
            }
        }
        blocks[position] = block
    }
}
