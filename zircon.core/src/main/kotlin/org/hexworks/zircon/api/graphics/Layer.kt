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
     * Same as [Layer.getTileAt] but will not use the offset of this [Layer]
     * (eg: just position instead of position - offset).
     */
    fun getRelativeTileAt(position: Position): Maybe<Tile>

    /**
     * Same as [Layer.setTileAt] but will not use the offset of this [Layer]
     * (eg: just position instead of position - offset).
     */
    fun setRelativeTileAt(position: Position, tile: Tile)

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
