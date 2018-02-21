package org.codetome.zircon.internal.game

import org.codetome.zircon.api.Block
import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.TextCharacter
import org.codetome.zircon.api.builder.TextCharacterBuilder
import org.codetome.zircon.api.builder.TextImageBuilder
import org.codetome.zircon.api.game.GameArea
import org.codetome.zircon.api.game.GameArea.BlockFetchMode
import org.codetome.zircon.api.game.Position3D
import org.codetome.zircon.api.game.Size3D
import org.codetome.zircon.api.graphics.TextImage
import org.codetome.zircon.internal.extensions.getIfPresent
import java.util.*

class InMemoryGameArea(private val size: Size3D,
                       private val layersPerBlock: Int,
                       private val filler: TextCharacter = TextCharacterBuilder.EMPTY) : GameArea {

    private val emptyBlock = (0 until layersPerBlock).map { filler }
    private val blocks: MutableMap<Position3D, MutableList<TextCharacter>> = TreeMap()

    override fun getSize() = size

    override fun fetchBlocks(fetchMode: BlockFetchMode): Iterable<Block> {
        return if (fetchMode == BlockFetchMode.IGNORE_EMPTY) {
            fetchBlocks()
        } else {
            size.fetchPositions().map {
                fetchBlockAtPosition(it)
            }
        }
    }

    override fun fetchBlocks(): Iterable<Block> {
        return blocks.map { (key, value) -> Block(key, value.toList()) }
    }

    override fun fetchBlocksAt(offset: Position3D, size: Size3D, fetchMode: BlockFetchMode): Iterable<Block> {
        return if (fetchMode == BlockFetchMode.IGNORE_EMPTY) {
            fetchBlocksAt(offset, size)
        } else {
            fetchPositionsWithOffset(offset, size)
                    .map { fetchBlockAtPosition(it) }
        }
    }

    override fun fetchBlocksAt(offset: Position3D, size: Size3D): Iterable<Block> {
        return fetchPositionsWithOffset(offset, size)
                .filter { blocks.containsKey(it) }
                .map { Block(it, blocks[it]!!.toList()) }
    }

    override fun fetchBlocksAt(z: Int, blockFetchMode: BlockFetchMode): Iterable<Block> {
        return if (blockFetchMode == BlockFetchMode.IGNORE_EMPTY) {
            fetchBlocksAt(z)
        } else {
            fetchPositionsWithOffset(
                    offset = Position3D.DEFAULT_POSITION,
                    size = Size3D.of(size.xLength, size.yLength, z))
                    .map { fetchBlockAtPosition(it) }
        }
    }

    override fun fetchBlocksAt(z: Int): Iterable<Block> {
        return blocks.keys.filter { it.z == z }
                .map { Block(it, blocks[it]!!.toList()) }
    }

    override fun fetchBlockAt(position: Position3D): Optional<Block> {
        return if (blocks.containsKey(position)) {
            Optional.of(Block(position, blocks[position]!!.toList()))
        } else {
            Optional.empty()
        }
    }

    override fun fetchCharacterAt(position: Position3D, layerIdx: Int): Optional<TextCharacter> {
        return blocks.getOrDefault(position, mutableListOf()).getIfPresent(layerIdx)
    }

    override fun fetchLayersAt(offset: Position3D, size: Size3D): Iterable<TextImage> {
        // TODO: param check here
        val offset2D = offset.to2DPosition()
        val window = size.to2DSize().fetchPositions()
        return (offset.z until size.zLength + offset.z).flatMap { z ->
            val images = mutableListOf<TextImage>()
            (0 until layersPerBlock).forEach { layerIdx ->
                val builder = TextImageBuilder().size(size.to2DSize())
                window.forEach { pos ->
                    fetchCharacterAt(Position3D.from2DPosition(pos + offset2D, z), layerIdx).map { char ->
                        builder.character(pos, char)
                    }
                }
                images.add(builder.build())
            }
            images
        }
    }

    override fun setBlockAt(position: Position3D, characters: List<TextCharacter>) {
        require(size.containsPosition(position)) {
            "The supplied position ($position) is not within the size ($size) of this game area."
        }
        require(characters.size <= layersPerBlock) {
            "The maximum number of layers per block for this game area is $layersPerBlock." +
                    " The supplied characters have a size of ${characters.size}"
        }
        val block = emptyBlock.toMutableList()
        characters.forEachIndexed {idx, char -> block[idx] = char}
        blocks[position] = block
    }

    override fun setCharacterAt(position: Position3D, layerIdx: Int, character: TextCharacter) {
        require(size.containsPosition(position)) {
            "The supplied position ($position) is not within the size ($size) of this game area."
        }
        require(blocks.containsKey(position)) {
            "There is no block at position $position."
        }
        require(layerIdx < layersPerBlock) {
            "This game area has $layersPerBlock layers per block. Supplied layer index is $layerIdx."
        }
        blocks.getOrElse(position, {
            throw IllegalArgumentException("Position ($position) is not present.")
        })[layerIdx] = character
    }

    private fun fetchPositionsWithOffset(offset: Position3D, size: Size3D) =
            size.fetchPositions()
                    .map { it.plus(offset) }

    private fun fetchBlockAtPosition(position: Position3D) =
            Block(position, blocks.getOrDefault(position, emptyBlock.toMutableList()).toList())
}
