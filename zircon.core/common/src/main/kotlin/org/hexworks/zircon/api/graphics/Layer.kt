package org.hexworks.zircon.api.graphics

import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.zircon.api.behavior.Clearable
import org.hexworks.zircon.api.behavior.Drawable
import org.hexworks.zircon.api.behavior.Movable
import org.hexworks.zircon.api.data.LayerSnapshot
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile

interface Layer : DrawSurface, Drawable, Movable, Clearable {

    val hiddenProperty: Property<Boolean>
    var isHidden: Boolean

    /**
     * Fetches all the (absolute) [Position]s which this
     * [Layer] contains.
     */
    fun fetchPositions(): Set<Position> = size.fetchPositions()
            .map { it + position }
            .toSet()

    /**
     * Same as [DrawSurface.getTileAt] but will consider the given [position]
     * as an absolute position (the position is relative to the top left corner
     * of the screen, not the top left corner of the [DrawSurface]).
     */
    fun getAbsoluteTileAt(position: Position): Maybe<Tile>

    /**
     * Same as [DrawSurface.setTileAt] but will consider the given [position]
     * as an absolute position (the position is relative to the top left corner
     * of the screen, not the top left corner of the [DrawSurface]).
     */
    fun setAbsoluteTileAt(position: Position, tile: Tile)

    /**
     * Creates a copy of this [Layer].
     */
    override fun createCopy(): Layer

    /**
     * Creates a snapshot of the current state of this [Layer].
     * A snapshot is useful to see a consistent state of a [Layer]
     * regardless of potential changes by other threads.
     */
    override fun createSnapshot(): LayerSnapshot

}
