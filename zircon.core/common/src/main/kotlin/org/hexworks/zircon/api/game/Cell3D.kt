package org.hexworks.zircon.api.game

import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.impl.Position3D
import org.hexworks.zircon.internal.game.DefaultCell3D

/**
 * Represents a [Block] which is at a given [Position3D]
 * in a [org.hexworks.zircon.api.game.GameArea].
 */
interface Cell3D<T : Tile, B : Block<T>> {

    val position: Position3D
    val block: B

    operator fun component1() = position

    operator fun component2() = block

    /**
     * Returns a copy of this [Cell3D] with the given `position`.
     */
    fun withPosition(position: Position3D): Cell3D<T, B>

    /**
     * Returns a copy of this [Cell3D] with the given `tile`.
     */
    fun withBlock(block: B): Cell3D<T, B>

    companion object {

        /**
         * Creates a new [Cell3D].
         */
        fun <T : Tile, B : Block<T>> create(position: Position3D, block: B): Cell3D<T, B> {
            return DefaultCell3D(position, block)
        }
    }
}
