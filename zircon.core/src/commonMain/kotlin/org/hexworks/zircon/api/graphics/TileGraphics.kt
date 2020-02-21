package org.hexworks.zircon.api.graphics

import org.hexworks.zircon.api.behavior.Copiable
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Rect
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile

/**
 * A [TileGraphics] enhances the [DrawSurface] interface with factory functions
 * for creating derived objects such as:
 * - [toSubTileGraphics]
 * - [toLayer]
 * - [toResized]
 * - [toTileImage]
 * and to be able to [createCopy] from this object.
 */
interface TileGraphics : Copiable<TileGraphics>, DrawSurface {

    /**
     * This function can be used to create an editable "window" over the underlying [TileGraphics].
     * Writing is restricted to the area represented by `rect` so if `rect` consists
     * of Position(1, 1) and Size(2, 2), the resulting [TileGraphics] will have a size
     * of (2, 2) and writing to it will write to the original graphics' surface, offset
     * by Position(1, 1).
     */
    fun toSubTileGraphics(rect: Rect): TileGraphics

    /**
     * Creates a **new** [Layer] from the contents of this [TileGraphics].
     * The result is not connected to the original object, which means that changes
     * to one are not reflected in the other.
     * @param offset the offset for the new [Layer], `(0, 0)` by default
     */
    fun toLayer(offset: Position = Position.zero()): Layer

    /**
     * Returns a copy of this [TileGraphics] resized to a new size and using
     * [Tile.empty] if the new size is larger than the old and
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

    /**
     * Creates a **new** [TileImage] from the contents of this
     * [TileGraphics]. The result is detached from the current one
     * which means that changes to one are not reflected in the
     * other.
     */
    fun toTileImage(): TileImage
}
