package org.codetome.zircon.api.graphics

import org.codetome.zircon.api.behavior.*
import org.codetome.zircon.api.data.Position
import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.resource.TilesetResource
import org.codetome.zircon.api.tileset.Tileset
import org.codetome.zircon.api.util.Maybe
import org.codetome.zircon.internal.behavior.impl.DefaultBoundable
import org.codetome.zircon.internal.behavior.impl.DefaultStyleable
import org.codetome.zircon.internal.behavior.impl.DefaultTilesetOverride

/**
 * this is a basic building block which can be re-used by complex image
 * classes like layers, boxes, components, and more
 * all classes which are implementing the DrawSurface or the Drawable operations can
 * use this class as a base class just like how the TileGrid uses it
 */

abstract class BaseTileImage(
        styleSet: StyleSet,
        tileset: TilesetResource<out Tile>,
        val size: Size,
        private val tilesetOverride: TilesetOverride = DefaultTilesetOverride(
                tileset = tileset),
        private val contents: MutableMap<Position, Tile>,
        styleable: Styleable = DefaultStyleable(styleSet),
        boundable: Boundable = DefaultBoundable(size = size))
    : TileImage,
        Styleable by styleable,
        Boundable by boundable,
        TilesetOverride by tilesetOverride {


    override fun clear() {
        contents.clear()
    }

    override fun getTileAt(position: Position): Maybe<Tile> {
        return if (containsPosition(position)) {
            Maybe.of(contents[position] ?: Tile.empty())
        } else {
            Maybe.empty()
        }
    }

    override fun setTileAt(position: Position, tile: Tile) {
        if (position.x < size.xLength && position.y < size.yLength) {
            contents[position] = tile
        }
    }

    override fun createSnapshot(): Map<Position, Tile> {
        return contents
    }

    override fun draw(drawable: Drawable, offset: Position) {
        drawable.drawOnto(this, offset)
    }

    override fun drawOnto(surface: DrawSurface, offset: Position) {
        contents.entries.forEach { (pos, tile) ->

            tile.drawOnto(surface, pos + offset)
        }
    }
}
