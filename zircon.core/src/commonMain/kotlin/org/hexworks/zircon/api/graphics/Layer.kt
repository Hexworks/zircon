package org.hexworks.zircon.api.graphics

import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.zircon.api.behavior.Movable
import org.hexworks.zircon.api.data.LayerState
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.internal.behavior.Identifiable

/**
 *A [Layer] is a [TileGraphics] which can be positioned and
 * moved over a [TileGrid]. With [Layer]s one can create a
 * quasi 3D effect (like top down oblique projections).
 * A [Layer] can also be hidden (invisible) by using either
 * [hiddenProperty] or [isHidden].
 */
interface Layer : Identifiable, Movable, TileGraphics {

    override val state: LayerState

    val hiddenProperty: Property<Boolean>
    var isHidden: Boolean

    /**
     * Creates a copy of this [Layer].
     */
    override fun createCopy(): Layer

    /**
     * Same as [TileGraphics.getTileAt] but will consider the given [position]
     * as an absolute position (the position is relative to the top left corner
     * of the screen, not the top left corner of the [TileGraphics]).
     */
    fun getAbsoluteTileAt(position: Position): Maybe<Tile>

    /**
     * Same as [TileGraphics.setTileAt] but will consider the given [position]
     * as an absolute position (the position is relative to the top left corner
     * of the screen, not the top left corner of the [TileGraphics]).
     */
    fun setAbsoluteTileAt(position: Position, tile: Tile)

}
