package org.codetome.zircon.api.shape

import org.codetome.zircon.api.data.Position
import org.codetome.zircon.internal.shape.DefaultShape
import org.codetome.zircon.poc.drawableupgrade.drawables.TileImage
import org.codetome.zircon.poc.drawableupgrade.tile.Tile
import org.codetome.zircon.poc.drawableupgrade.tileset.Tileset

/**
 * A [Shape] is a set of [org.codetome.zircon.api.data.Position]s representing a geometric formation
 * (line, triangle, rectangle, box, etc). A [Shape] is the most abstract representation of any graphic
 * object in Zircon and has no associated grid, style, nor characters thus it is useful for
 * templating (like creating multiple versions of the same rectangle with different colors, shades
 * and characters).
 * Any [Shape] implementation is supposed to be immutable!
 */
interface Shape : Collection<Position> {

    /**
     * Returns all the [Position]s this [Shape] contains.
     */
    fun getPositions(): Set<Position>

    /**
     * Combines two [Shape]s into a new one which means that this operation
     * creates an union of the [Position]s of both [Shape]s.
     * **Note that** the original [Shape]s are unmodified, this operation
     * creates a new [Shape].
     */
    operator fun plus(shape: Shape): Shape {
        return DefaultShape(getPositions().plus(shape.getPositions()))
    }

    /**
     * Creates a [TileImage] from this [Shape] using `tile` to fill the positions.
     */
    fun <T: Any, S: Any> toTextImage(tile: Tile<T>, tileset: Tileset<T, S>) : TileImage<T, S>

    /**
     * Offsets this [Shape] to the default position (column=0,row=0),
     * so offsetting a Shape which contains `(Position(2, 3), Position(3, 4))`
     * will be transformed to `((Position(0, 0), Position(1, 1))`.
     */
    fun offsetToDefaultPosition() : Shape
}
