@file:Suppress("RUNTIME_ANNOTATION_NOT_SUPPORTED")

package org.hexworks.zircon.api.game

import org.hexworks.cobalt.core.api.behavior.Disposable
import org.hexworks.zircon.api.behavior.Scrollable3D
import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Size3D
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.internal.game.InternalGameArea

/**
 * A [GameArea] represents the 3D space in which the entities of a game take place.
 * The space is composed of [Block]s which are just voxels (like in Minecraft) which
 * have 6 sides (all optional), and a content [Tile] within the voxel itself (optional as well).
 * If it is no longer used it should be [dispose]d to free resources.
 */

interface GameArea<T : Tile, B : Block<T>> : Scrollable3D, Disposable {

    /**
     * Contains **all** the currently present [Block]s in this [GameArea].
     * Note for implementors: the [Map] should support consistent snapshots,
     * eg: iterating over its contents should not change if the underlying state changes.
     */
    val blocks: Map<Position3D, B>

    /**
     * Tells whether there is an actual [Block] at the given [position].
     * This means that the block in the given position is not a `filler`
     * block.
     */
    fun hasBlockAt(position: Position3D): Boolean

    /**
     * Returns the [Block] at the given [position] or `null` if no [Block] is present.
     */
    fun fetchBlockAtOrNull(position: Position3D): B?

    /**
     * Returns the [Block] at the given [position] if present or the result of calling
     * [orElse] with [position] if not.
     */
    fun fetchBlockAtOrElse(position: Position3D, orElse: (position: Position3D) -> B): B {
        return fetchBlockAtOrNull(position) ?: orElse(position)
    }

    /**
     * Sets the [Block] at the given [position]. Has no effect
     * if [position] is outside of the [actualSize] of this [GameArea].
     */
    fun setBlockAt(position: Position3D, block: B)

    fun asInternalGameArea(): InternalGameArea<T, B>

    companion object {

        internal fun fetchPositionsWithOffset(offset: Position3D, size: Size3D) =
            size.fetchPositions().map { it.plus(offset) }

    }

}
