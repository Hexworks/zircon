package org.hexworks.zircon.api.graphics

import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.zircon.api.behavior.Clearable
import org.hexworks.zircon.api.behavior.Drawable
import org.hexworks.zircon.api.behavior.Movable
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile

interface Layer : DrawSurface, Drawable, Movable, Clearable {

    override val size: Size
    override val width: Int
    override val height: Int

    val hiddenProperty: Property<Boolean>
    var isHidden: Boolean

    fun hide() {
        isHidden = true
    }

    fun show() {
        isHidden = false
    }

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
    fun createCopy(): Layer

    /**
     * Copies this [Layer] to a new immutable [TileImage].
     */
    fun toTileImage(): TileImage

    /**
     * Copies this [Layer] to a new [TileGraphics].
     */
    fun toTileGraphics(): TileGraphics

    /**
     * Transforms all of the [Tile]s in this [Layer] with the given
     * [transformer] and overwrites them with the results of calling
     * [transformer].
     */
    fun transform(transformer: (Tile) -> Tile)

    /**
     * Fills the empty positions of this [Layer] with the
     * given `filler` [Tile].
     */
    fun fill(filler: Tile): Layer

}
