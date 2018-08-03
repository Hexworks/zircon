package org.codetome.zircon.api.graphics

import org.codetome.zircon.api.behavior.Movable
import org.codetome.zircon.api.data.Position
import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.util.Maybe

interface Layer : TileImage, Movable {

    /**
     * Fetches all the (absolute) [Position]s which this
     * [Layer] contains.
     */
    fun fetchPositions(): Set<Position> = getBoundableSize().fetchPositions()
            .map { it + getPosition() }
            .toSet()

    override fun fetchFilledPositions(): List<Position>

    /**
     * Copies this [Layer].
     */
    fun createCopy(): Layer

    /**
     * Same as [Layer.getTileAt] but will not use the offset of this [Layer]
     * (eg: just position instead of position - offset).
     */
    fun getRelativeTileAt(position: Position): Maybe<Tile>

    /**
     * Same as [Layer.setTileAt] but will not use the offset of this [Layer]
     * (eg: just position instead of position - offset).
     */
    fun setRelativeTileAt(position: Position, character: Tile)

    override fun fill(filler: Tile): Layer

}
