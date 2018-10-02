package org.hexworks.zircon.api.graphics

import org.hexworks.zircon.api.behavior.DrawSurface
import org.hexworks.zircon.api.behavior.Drawable
import org.hexworks.zircon.api.behavior.Movable
import org.hexworks.zircon.api.behavior.TilesetOverride
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.util.Maybe

interface Layer : DrawSurface, Drawable, Movable {

    /**
     * Fetches all the (absolute) [Position]s which this
     * [Layer] contains.
     */
    fun fetchPositions(): Set<Position> = size().fetchPositions()
            .map { it + position() }
            .toSet()

    /**
     * Same as [DrawSurface.getTileAt] but will consider the given `position`
     * as an absolute position (the position is relative to the top left corner
     * of the screen, not the top left corner of the [DrawSurface]).
     */
    fun getAbsoluteTileAt(position: Position): Maybe<Tile>

    /**
     * Same as [DrawSurface.setTileAt] but will consider the given `position`
     * as an absolute position (the position is relative to the top left corner
     * of the screen, not the top left corner of the [DrawSurface]).
     */
    fun setAbsoluteTileAt(position: Position, tile: Tile)

    /**
     * Creates a copy of this [Layer].
     */
    fun createCopy(): Layer

    /**
     * Fills the empty positions of this [Layer] with the
     * given `filler` [Tile].
     */
    fun fill(filler: Tile): Layer

}
