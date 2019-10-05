package org.hexworks.zircon.api.graphics

import org.hexworks.zircon.api.Positions
import org.hexworks.zircon.api.Tiles
import org.hexworks.zircon.api.behavior.Clearable
import org.hexworks.zircon.api.builder.graphics.TileGraphicsBuilder
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Rect
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.TileGraphicsState

/**
 * Represents a [TileComposite] which can be *drawn* upon. A [TileGraphics] is the most
 * basic interface for all drawable surfaces which exposes simple get, set and draw
 * functions, and other useful operations for transforming it.
 */
interface TileGraphics : Clearable, DrawSurface, TileComposite {

    /**
     * Holds a snapshot of the current state of this [TileGraphics].
     */
    val state: TileGraphicsState

    /**
     * Creates a new [TileGraphics] which will use this one as the underlying [TileGraphics].
     * Writing is restricted to the area represented by `rect` so if `rect` consists
     * of Position(1, 1) and Size(2, 2), the resulting [TileGraphics] will have a size
     * of (2, 2) and writing to it will write to the original graphics' surface, offset
     * by Position(1, 1).
     */
    fun toSubTileGraphics(rect: Rect): TileGraphics

    /**
     * Creates a **new** [Layer] from the contents of this
     * [TileGraphics]. The result is detached from the current one
     * which means that changes to one are not reflected in the
     * other.
     * @param offset the offset for the new [Layer], `(0, 0)` by default
     */
    fun toLayer(offset: Position = Positions.zero()): Layer

    /**
     * Creates a **new** copy from the contents of this
     * [TileGraphics]. The result is detached from the current one
     * which means that changes to one are not reflected in the
     * other.
     */
    fun createCopy(): TileGraphics

    /**
     * Returns a copy of this [TileGraphics] resized to a new size and using
     * [Tiles.empty] if the new size is larger than the old and
     * it needs to fill in empty areas. The copy will be independent from the
     * one this method is invoked on, so modifying one will not affect the other.
     */
    fun toResized(newSize: Size): TileGraphics

    /**
     * Returns a copy of this image resized to a new size and using
     * the specified [filler] [Tile] if the new size is larger than the old one
     * and we need to fill in empty areas. The copy will be independent from
     * the one this method is invoked on, so modifying one will not affect the other.
     */
    fun toResized(newSize: Size, filler: Tile): TileGraphics
}
