package org.hexworks.zircon.api.game

import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.zircon.api.behavior.Scrollable3D
import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.impl.Position3D
import org.hexworks.zircon.api.data.impl.Size3D

/**
 * A [GameArea] represents the 3D space in which the entities of a game take place.
 * The space is composed of [Block]s which are just voxels (like in Minecraft) which
 * have 6 sides (all optional), and a content tile within the voxel itself (optional as well).
 */
interface GameArea<T : Tile, B : Block<T>> : Scrollable3D {

    /**
     * The default block which is used in this [GameArea].
     */
    val defaultBlock: B

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
    fun fetchBlocks(): Map<Position3D, B>

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
    fun fetchBlocksAt(offset: Position3D, size: Size3D): Map<Position3D, B>

    /**
     * Returns the [Block]s at the given `z` level.
     * Empty positions are ignored.
     */
    fun fetchBlocksAtLevel(z: Int): Map<Position3D, B>

    /**
     * Sets the [Tile]s at the given position. Text characters are ordered
     * as layers from bottom to top.
     */
    fun setBlockAt(position: Position3D, block: B)

    companion object {

        internal fun fetchPositionsWithOffset(offset: Position3D, size: Size3D) =
                size.fetchPositions()
                        .map { it.plus(offset) }

    }

}
