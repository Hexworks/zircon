package org.hexworks.zircon.api.graphics

import org.hexworks.zircon.api.behavior.Clearable
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Rect
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile

/**
 * A [TileGraphics] is a [DrawSurface] which is also a [Drawable]
 * eg: it can be drawn upon other [DrawSurface]s. like a
 * [org.hexworks.zircon.api.grid.TileGrid]. A [TileGraphics] is an
 * in-memory object and it is not attached to any other Zircon object
 * by default.
 */
interface TileGraphics
    : Clearable, DrawSurface {

    /**
     * Creates a new [TileGraphics] which will use this one as the underlying [TileGraphics].
     * Writing is restricted to the area denoted by `bounds` so if `bounds` consists
     * of Position(1, 1) and Size(2, 2), the resulting [TileGraphics] will have a size
     * of (2, 2) and writing to it will write to the original graphics' surface, offset
     * by Position(1, 1).
     */
    fun toSubTileGraphics(rect: Rect): TileGraphics

    override fun createCopy(): TileGraphics = super.createCopy().toTileGraphics()

    override fun toResized(newSize: Size): TileGraphics = super.toResized(newSize).toTileGraphics()

    override fun toResized(newSize: Size, filler: Tile): DrawSurface = super.toResized(newSize, filler).toTileGraphics()
}
