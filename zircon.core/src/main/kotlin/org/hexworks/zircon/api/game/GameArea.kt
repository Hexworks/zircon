package org.hexworks.zircon.api.game

import org.hexworks.zircon.api.builder.graphics.TileGraphicsBuilder
import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.impl.Position3D
import org.hexworks.zircon.api.data.impl.Size3D
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.api.kotlin.map
import org.hexworks.zircon.api.util.Maybe
import org.hexworks.zircon.internal.extensions.getIfPresent

/**
 * A [GameArea] represents the 3D space in which the entities of a
 * game take place. The space is composed of [Block]s which are just
 * cubes (like in Minecraft) which have 6 sides (all optional), and
 * layers within the cube itself (optional as well).
 */
interface GameArea<T : Tile, B : Block<T>> {

    /**
     * The default block which is used in this [GameArea]
     */
    val defaultBlock: B
    /**
     * Returns the size of the 3D space this [GameArea] represents.
     */
    val size: Size3D

    /**
     * Tells how many layers are in each [Block].
     * **Note that** front/back/top/bottom characters are not considered
     * a layer!
     */
    fun layersPerBlock(): Int

    /**
     * Tells whether there is an actual [Block] at the given `position`.
     * This means that the block in the given position is not a `filler`
     * block.
     */
    fun hasBlockAt(position: Position3D): Boolean

    /**
     * Returns the [Block] at the given `position` (if any).
     */
    fun fetchBlockAt(position: Position3D): Maybe<B>

    /**
     * Returns the [Block] at the given `position` if present,
     * otherwise returns the default block (an empty block by default).
     */
    fun fetchBlockOrDefault(position: Position3D): B

    /**
     * Returns **all** the [Block]s in this [GameArea].
     * Empty positions are **ignored**.
     */
    fun fetchBlocks(): Iterable<Cell3D<T, B>>

    /**
     * Returns **all** the [Block]s in this [GameArea].
     * Empty positions are either ignored or filled with a filler character,
     * depending on the `fetchMode` supplied.
     */
    fun fetchBlocks(fetchMode: BlockFetchMode): Iterable<Cell3D<T, B>> {
        return if (fetchMode == BlockFetchMode.IGNORE_EMPTY) {
            fetchBlocks()
        } else {
            size.fetchPositions().map { createCell(it) }
        }
    }

    /**
     * Returns a part of this [GameArea] as a sequence of [Block]s.
     * Empty positions are **ignored**.
     *
     * @param offset the position where the collection of Blocks will start.
     * @param size the size of the area which you need the blocks from.
     *
     * Example: offset=(x=2, y=4, z=8), size=(xLength=9,yLength=3,zLength=4)
     *<pre>
     *         ^ (2,4,12) (z)
     *         \
     *         \
     *         \
     *         \
     *  (2,4,8)O---------> (11,4,8) (x)
     *        /
     *      /
     *    /
     *  L (2,7,8) (y)
     *</pre>
     */
    fun fetchBlocksAt(offset: Position3D, size: Size3D): Iterable<Cell3D<T, B>> {
        return fetchPositionsWithOffset(offset, size)
                .asSequence()
                .filter { hasBlockAt(it) }
                .map { createCell(it) }
                .toList()
    }

    /**
     * Returns a part of this [GameArea] as a sequence of [Block]s.
     *
     * @param offset the position where the collection of Blocks will start.
     * @param size the size of the area which you need the blocks from.
     * @param fetchMode the [BlockFetchMode] to use.
     */
    fun fetchBlocksAt(offset: Position3D, size: Size3D, fetchMode: BlockFetchMode): Iterable<Cell3D<T, B>> {
        return if (fetchMode == BlockFetchMode.IGNORE_EMPTY) {
            fetchBlocksAt(offset, size)
        } else {
            fetchPositionsWithOffset(offset, size)
                    .map { createCell(it) }
        }
    }

    /**
     * Returns the [Block]s at the given `z` level.
     * Empty positions are **ignored**.
     */
    fun fetchBlocksAtLevel(z: Int): Iterable<Cell3D<T, B>> {
        return fetchBlocks()
                .filter { it.position.z == z }
                .map { createCell(it.position) }
    }

    /**
     * Returns the [Block]s at the given `z` level.
     * Empty positions are either ignored, or a default filler value is returned.
     */
    fun fetchBlocksAtLevel(z: Int, blockFetchMode: BlockFetchMode): Iterable<Cell3D<T, B>> {
        return if (blockFetchMode == BlockFetchMode.IGNORE_EMPTY) {
            fetchBlocksAtLevel(z)
        } else {
            fetchPositionsWithOffset(
                    offset = Position3D.defaultPosition(),
                    size = Size3D.create(size.xLength, size.yLength, z))
                    .map { createCell(it) }
        }
    }

    /**
     * Returns the [Tile] at the given `position` and `layerIdx` (if any).
     */
    fun fetchTileAt(position: Position3D, layerIdx: Int): Maybe<T> {
        return fetchBlockOrDefault(position).layers.getIfPresent(layerIdx)
    }

    /**
     * Returns all the layers from bottom to top as a collection of [org.hexworks.zircon.api.graphics.TileGraphics]s.
     * A layer is a collection of [Tile]s at a given `z` level and `layerIndex`.
     */
    fun fetchLayersAt(offset: Position3D, size: Size3D): Iterable<TileGraphics> {
        val offset2D = offset.to2DPosition()
        val window = size.to2DSize().fetchPositions()
        return (offset.z until size.zLength + offset.z).flatMap { z ->
            val images = mutableListOf<TileGraphics>()
            (0 until layersPerBlock()).forEach { layerIdx ->
                val builder = TileGraphicsBuilder.newBuilder().withSize(size.to2DSize())
                window.forEach { pos ->
                    fetchTileAt(Position3D.from2DPosition(pos + offset2D, z), layerIdx).map { char ->
                        builder.withTile(pos, char)
                    }
                }
                images.add(builder.build())
            }
            images
        }
    }

    /**
     * Creates a [Cell3D] representing the [Block] at the given `position`.
     */
    fun createCell(position: Position3D): Cell3D<T, B> {
        return Cell3D.create(position, fetchBlockOrDefault(position))
    }

    /**
     * Sets the [Tile]s at the given position. Text characters are ordered
     * as layers from bottom to top.
     */
    fun setBlockAt(position: Position3D, block: B)

    /**
     * The fetch mode for [Block]s.
     */
    enum class BlockFetchMode {

        /**
         * In this fetch mode empty blocks are not returned
         * when bulk-fetching blocks.
         */
        IGNORE_EMPTY,
        /**
         * In this fetch mode empty blocks are returned
         * with the contents set from the `filler` value.
         */
        FETCH_EMPTY
    }

    companion object {

        fun fetchPositionsWithOffset(offset: Position3D, size: Size3D) =
                size.fetchPositions()
                        .map { it.plus(offset) }

    }

}
