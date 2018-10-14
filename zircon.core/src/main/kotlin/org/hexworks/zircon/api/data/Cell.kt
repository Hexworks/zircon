package org.hexworks.zircon.api.data

import org.hexworks.zircon.internal.data.DefaultCell

/**
 * Represents a [Tile] which is at a given [Position]
 * on a [org.hexworks.zircon.api.behavior.DrawSurface].
 */
interface Cell {

    val position: Position
    val tile: Tile

    operator fun component1() = position

    operator fun component2() = tile

    /**
     * Returns a copy of this [Cell] with the given `position`.
     */
    fun withPosition(position: Position): Cell

    /**
     * Returns a copy of this [Cell] with the given `tile`.
     */
    fun withTile(tile: Tile): Cell

    companion object {

        /**
         * Creates a new [Cell].
         */
        fun create(position: Position, tile: Tile): Cell {
            return DefaultCell(position, tile)
        }
    }
}
