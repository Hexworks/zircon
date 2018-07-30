package org.codetome.zircon.api.graphics

import org.codetome.zircon.api.data.Position
import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.behavior.TilesetOverride
import org.codetome.zircon.api.behavior.Movable
import org.codetome.zircon.api.util.Maybe
import org.codetome.zircon.internal.tileset.impl.FontSettings

/**
 * A [Layer] is a specialized [TileImage] which is drawn upon a
 * [org.codetome.zircon.api.behavior.Layerable] object. A [Layer] differs from a [TileImage] in
 * the way it is handled. It can be repositioned relative to its
 * parent while a [TileImage] cannot.
 */
interface Layer : TileImage, Movable, TilesetOverride {

    /**
     * Fetches all the (absolute) [Position]s which this
     * [Layer] contains.
     */
    fun fetchPositions(): Set<Position>

    /**
     * Fetches all the (absolute) [Position]s which this
     * [Layer] contains and is not `EMPTY`.
     */
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

    companion object {

        fun defaultFont() = FontSettings.NO_FONT

        fun defaultSize() = Size.one()

        fun defaultFiller() = Tile.empty()

    }
}
