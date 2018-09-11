package org.hexworks.zircon.api.graphics

import org.hexworks.zircon.api.behavior.*
import org.hexworks.zircon.api.data.Bounds
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.util.Maybe
import org.hexworks.zircon.internal.behavior.impl.DefaultBoundable
import org.hexworks.zircon.internal.behavior.impl.DefaultStyleable
import org.hexworks.zircon.internal.behavior.impl.DefaultTilesetOverride
import org.hexworks.zircon.internal.graphics.DefaultTileImage

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
                styleSet = backend.styleSet()),
        private val tilesetOverride: TilesetOverride = DefaultTilesetOverride(
                tileset = backend.tileset()))
    : TileGraphics, Boundable by boundable, Styleable by styleable, TilesetOverride by tilesetOverride {

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

    override fun fetchFilledPositions(): List<Position> {
        return backend.snapshot().filterKeys { bounds.containsPosition(it) }.keys.map { it - offset }
    }

    override fun resize(newSize: Size) = restrictOperation()

    override fun resize(newSize: Size, filler: Tile) = restrictOperation()

    override fun applyStyle(styleSet: StyleSet, bounds: Bounds, keepModifiers: Boolean, applyToEmptyCells: Boolean) {
        super.applyStyle(
                styleSet = styleSet,
                // this is needed because I don't want to reimplement applyStyle...
                bounds = bounds.withPosition(Position.defaultPosition()),
                keepModifiers = keepModifiers,
                applyToEmptyCells = applyToEmptyCells)
    }

    override fun toTileImage(): TileImage {
        return DefaultTileImage(
                size = size(),
                tileset = tileset(),
                tiles = fetchCells().map { it.position to it.tile }.toMap())
    }

    override fun clear() {
        size.fetchPositions().forEach {
            backend.setTileAt(it + offset, Tile.empty())
        }
    }

    override fun getTileAt(position: Position): Maybe<Tile> {
        return if (size.containsPosition(position)) {
            return backend.getTileAt(position + offset)
        } else Maybe.empty()
    }

    override fun setTileAt(position: Position, tile: Tile) {
        if (size.containsPosition(position)) {
            backend.setTileAt(position + offset, tile)
        }
    }

    override fun snapshot(): Map<Position, Tile> {
        throw UnsupportedOperationException("Sub tile images don't support snapshots.")
    }

    override fun draw(drawable: Drawable, position: Position) {
        restrictOperation()
    }

    override fun drawOnto(surface: DrawSurface, position: Position) {
        bounds.size().fetchPositions().forEach { pos ->
            getTileAt(pos).map { tile ->
                surface.setTileAt(pos + offset + position, tile)
            }
        }
    }

    private fun restrictOperation(): Nothing {
        throw UnsupportedOperationException("This operation is not supported for sub tile graphics.")
    }

}
