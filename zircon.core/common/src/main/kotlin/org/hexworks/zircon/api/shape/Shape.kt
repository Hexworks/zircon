package org.hexworks.zircon.api.shape

import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.shape.DefaultShape

/**
 * A [Shape] is a set of [org.hexworks.zircon.api.data.Position]s representing a geometric formation
 * (line, triangle, rectangle, box, etc). A [Shape] is the most abstract representation of any graphic
 * object in Zircon and has no associated grid, style, nor characters thus it is useful for
 * templating (like creating multiple versions of the same rectangle with different colors, shades
 * and characters).
 * Any [Shape] implementation is supposed to be immutable!
 */
interface Shape : Collection<Position> {

    /**
     * All the [Position]s this [Shape] contains.
     */
    val positions: Set<Position>

    /**
     * Combines two [Shape]s into a new one which means that this operation
     * creates an union of the [Position]s of both [Shape]s.
     * **Note that** the original [Shape]s are unmodified, this operation
     * creates a new [Shape].
     */
    operator fun plus(shape: Shape): Shape {
        return DefaultShape(positions.plus(shape.positions))
    }

    /**
     * Creates a [TileGraphics] from this [Shape] using `tile` to fill the positions.
     */
    fun toTileGraphics(tile: Tile, tileset: TilesetResource): TileGraphics

    /**
     * Offsets this [Shape] to the default position (column=0,row=0),
     * so offsetting a Shape which contains `(Position(2, 3), Position(3, 4))`
     * will be transformed to `((Position(0, 0), Position(1, 1))`.
     * Returns a new [Shape] with the new positions.
     */
    fun offsetToDefaultPosition(): Shape
}
