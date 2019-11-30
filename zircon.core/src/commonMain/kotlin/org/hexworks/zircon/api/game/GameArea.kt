package org.hexworks.zircon.api.game

import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.zircon.api.behavior.Scrollable3D
import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.impl.Position3D
import org.hexworks.zircon.api.data.Size3D

/**
 * A [GameArea] represents the 3D space in which the entities of a game take place.
 * The space is composed of [Block]s which are just voxels (like in Minecraft) which
 * have 6 sides (all optional), and a content [Tile] within the voxel itself (optional as well).
 */
interface GameArea<T : Tile, B : Block<T>> : Scrollable3D {

    /**
     * Contains **all** the currently present [Block]s in this [GameArea].
     * Note for implementors: the [Map] should support consistent snapshots,
     * eg: iterating over its contents should not change if the underlying state changes.
     */
    val blocks: Map<Position3D, B>

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
     * Returns a part of this [GameArea] as a sequence of [Block]s. Note that
     * this operation creates a consistent snapshot, eg: modifying the underlying
     * [GameArea] has no effect on the generated [Sequence].
     *
     * The returned [Sequence]
     *
     * @param offset the position where the collection of Blocks will start.
     * @param size the size of the area which you need the blocks from.
     *
     * Example: offset=(x=2, y=4, z=8), size=(xLength=9,yLength=3,zLength=4)
     *<pre>
     *          ^ (2,4,12) (z)
     *          \
     *          \
     *          \
     *          \
     *  (2,4,8) O---------> (11,4,8) (x)
     *         /
     *       /
     *     /
     *   L (2,7,8) (y)
     *</pre>
     */
    fun fetchBlocksAt(offset: Position3D, size: Size3D): Sequence<Pair<Position3D, B>>

    /**
     * Returns a [Sequence] of the currently present [Block]s at the given [z] level.
     * Note that this operation creates a consistent snapshot, eg: modifying the underlying
     * [GameArea] has no effect on the generated [Sequence].
     */
    fun fetchBlocksAtLevel(z: Int): Sequence<Pair<Position3D, B>>

    /**
     * Sets the [Block] at the given [position]. Has no effect
     * if [position] is outside of the [actualSize] of this [GameArea].
     */
    fun setBlockAt(position: Position3D, block: B)

    companion object {

        internal fun fetchPositionsWithOffset(offset: Position3D, size: Size3D) =
                size.fetchPositions().map { it.plus(offset) }

    }

}
