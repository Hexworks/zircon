package org.hexworks.zircon.api.graphics

import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile

/**
 * Represents an object which is composed of [Tile]s and has a [Size].
 */
interface TileComposite {

    /**
     * The size of this [TileComposite] on a 2D plane.
     */
    val size: Size

    /**
     * Shorthand for [Size.width]
     */
    val width: Int
        get() = size.width

    /**
     * Shorthand for [Size.height]
     */
    val height: Int
        get() = size.height

    /**
     * Returns the character stored at a particular position in this [TileComposite].
     * Returns an empty [Maybe] if no [Tile] is present at the given [Position].
     */
    fun getTileAt(position: Position): Maybe<Tile>
}
