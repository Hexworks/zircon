package org.codetome.zircon.api.graphics

import org.codetome.zircon.api.behavior.Boundable
import org.codetome.zircon.api.behavior.Styleable
import org.codetome.zircon.internal.behavior.impl.DefaultStyleable
import org.codetome.zircon.api.behavior.DrawSurface
import org.codetome.zircon.api.behavior.Drawable
import org.codetome.zircon.api.data.Position
import org.codetome.zircon.api.data.Size
import org.codetome.zircon.internal.behavior.impl.DefaultBoundable
import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.tileset.Tileset
import java.util.*

/**
 * this is a basic building block which can be re-used by complex image
 * classes like layers, boxes, components, and more
 * all classes which are implementing the DrawSurface or the Drawable operations can
 * use this class as a base class just like how the TileGrid uses it
 */

abstract class BaseTileImage<T : Any, S : Any>(
        styleSet: StyleSet,
        val size: Size,
        var tileset: Tileset<T, S>,
        private val contents: MutableMap<Position, Tile<T>>,
        styleable: Styleable = DefaultStyleable(styleSet),
        boundable: Boundable = DefaultBoundable(size = size))
    : TileImage<T, S>,
        Styleable by styleable,
        Boundable by boundable{

    override fun tileset() = tileset

    override fun useTileset(tileset: Tileset<T, S>) {
        this.tileset = tileset
    }

    override fun clear() {
        contents.clear()
    }

    override fun getTileAt(position: Position): Optional<Tile<T>> {
        return Optional.ofNullable(contents[position])
    }

    override fun setTileAt(position: Position, tile: Tile<T>) {
        if (position.x < size.xLength && position.y < size.yLength) {
            contents[position] = tile
        }
    }

    override fun createSnapshot(): Map<Position, Tile<T>> {
        return contents
    }

    override fun draw(drawable: Drawable<T>, offset: Position) {
        drawable.drawOnto(this, offset)
    }

    override fun drawOnto(surface: DrawSurface<T>, offset: Position) {
        contents.entries.forEach { (pos, tile) ->

            tile.drawOnto(surface, pos + offset)
        }
    }
}
