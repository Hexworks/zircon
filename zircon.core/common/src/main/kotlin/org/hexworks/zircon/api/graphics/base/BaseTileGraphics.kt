package org.hexworks.zircon.api.graphics.base

import org.hexworks.zircon.api.Positions
import org.hexworks.zircon.api.Tiles
import org.hexworks.zircon.api.behavior.TilesetOverride
import org.hexworks.zircon.api.data.Rect
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.DrawSurface
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.api.graphics.impl.SubTileGraphics
import org.hexworks.zircon.api.resource.TilesetResource

/**
 * This base class for [TileGraphics] can be re-used by complex image
 * classes like layers, boxes, components, and more.
 * All classes which are implementing the [DrawSurface] or the [Drawable] operations can
 * use this class as a base class.
 */
abstract class BaseTileGraphics(
        initialTileset: TilesetResource,
        initialSize: Size)
    : TileGraphics,
        TilesetOverride {

    override var tileset: TilesetResource = initialTileset
        set(value) {
            value.checkCompatibilityWith(tileset)
            field = value
        }

    override val size = initialSize

    override fun toString(): String {
        val currTiles = tiles
        return (0 until height).joinToString("") { y ->
            (0 until width).joinToString("") { x ->
                (currTiles[Positions.create(x, y)] ?: Tiles.defaultTile())
                        .asCharacterTile()
                        .orElse(Tiles.defaultTile())
                        .character.toString()
            }.plus("\n")
        }.trim()
    }

    override fun toSubTileGraphics(
            rect: Rect): SubTileGraphics {
        return SubTileGraphics(
                rect = rect,
                backend = this)
    }
}
