package org.hexworks.zircon.api.graphics

import org.hexworks.zircon.api.behavior.Boundable
import org.hexworks.zircon.api.behavior.DrawSurface
import org.hexworks.zircon.api.behavior.Drawable
import org.hexworks.zircon.api.behavior.Styleable
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.data.Bounds
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.modifier.Modifier
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.util.Maybe
import org.hexworks.zircon.internal.behavior.impl.DefaultBoundable
import org.hexworks.zircon.internal.behavior.impl.DefaultStyleable

/**
 * this is a basic building block which can be re-used by complex image
 * classes like layers, boxes, components, and more
 * all classes which are implementing the DrawSurface or the Drawable operations can
 * use this class as a base class just like how the TileGrid uses it
 */

class SubTileGraphics(
        private val backend: TileGraphics,
        private val bounds: Bounds,
        private val boundable: Boundable = DefaultBoundable(
                size = bounds.size(),
                position = bounds.position()),
        private val styleable: Styleable = DefaultStyleable(
                styleSet = backend.styleSet()))
    : TileGraphics, Boundable by boundable, Styleable by styleable {

    private val offset = bounds.position()
    private val size = bounds.size()

    init {
        require(size < backend.size()) {
            "The size of a sub tile graphics can't be bigger than the original tile graphics."
        }
        require(offset.toSize() + size < backend.size()) {
            "sub tile graphics offset ($offset) is too big for size '$size'."
        }
    }

    override fun clear() {
        size.fetchPositions().forEach {
            backend.setTileAt(it + offset, Tile.empty())
        }
    }

    override fun getTileAt(position: Position): Maybe<Tile> {
        return if(size.containsPosition(position)) {
            return backend.getTileAt(position + offset)
        } else Maybe.empty()
    }

    override fun setTileAt(position: Position, tile: Tile) {
        backend.setTileAt(position + offset, tile)
    }

    override fun snapshot(): Map<Position, Tile> {
        throw UnsupportedOperationException("Sub tile images don't support snapshots.")
    }

    override fun draw(drawable: Drawable, position: Position) {
        restrictDraw()
    }


    override fun drawOnto(surface: DrawSurface, position: Position) {
        TODO("not implemented")
    }


    override fun tileset(): TilesetResource {
        TODO("not implemented")
    }

    override fun useTileset(tileset: TilesetResource) {
        TODO("not implemented")
    }

    private fun restrictDraw() {
        throw UnsupportedOperationException(
                "Drawing onto sub tile graphics is not supported due to the inability" +
                        " to determine the drawable's size which would result in drawing" +
                        " to restricted positions on the underlying tile graphics")
    }

}
