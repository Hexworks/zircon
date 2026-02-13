package org.hexworks.zircon.api.data

import kotlinx.collections.immutable.toPersistentList
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.internal.data.DefaultStackedTile

/**
 * This tile implementation contains a *stack* of [Tile]s. This can be used to
 * create compositions of [Tile]s that will be rendered on top of each other
 * (form bottom to top) without having to use [Layer]s.
 *
 * A [StackedTile] must have at least **one** [Tile] in its stack ([tiles])
 * therefore, any implementation of this interface must ensure this property.
 *
 * @see Tile
 */

interface StackedTile : Tile {

    /**
     * The [Tile] that's the base of this [StackedTile].
     */
    val baseTile: Tile

    /**
     * The [Tile]s that are stacked on top of the [baseTile].
     */
    val rest: List<Tile>

    /**
     * The [Tile] on top of this stack. This tile is always visible.
     */
    val top: Tile

    /**
     * Contains all the tiles in this [StackedTile] from top to bottom.
     */
    val tiles: List<Tile>


    companion object {

        /**
         * Creates a new [StackedTile] form the given [baseTile] and [rest].
         * @param rest use this parameter if you want to add multiple tiles apart from the [baseTile].
         * [rest] is ordered from bottom to top (vertically)
         */
        fun create(
            baseTile: Tile,
            vararg rest: Tile
        ): StackedTile = DefaultStackedTile(
            baseTile = baseTile,
            rest = rest.toList().toPersistentList()
        )
    }
}
