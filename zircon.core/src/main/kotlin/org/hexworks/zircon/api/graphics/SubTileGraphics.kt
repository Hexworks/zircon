package org.hexworks.zircon.api.graphics

import org.hexworks.zircon.api.behavior.*
import org.hexworks.zircon.api.data.*
import org.hexworks.zircon.api.kotlin.map
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
        rect: Rect,
        private val backend: TileGraphics,
        private val boundable: Boundable = DefaultBoundable(
                size = rect.size,
                position = rect.position),
        private val styleable: Styleable = DefaultStyleable(
                styleSet = backend.toStyleSet()),
        private val tilesetOverride: TilesetOverride = DefaultTilesetOverride(
                tileset = backend.currentTileset()))
    : TileGraphics, Boundable by boundable, Styleable by styleable, TilesetOverride by tilesetOverride {

    override val size: Size
        get() = rect.size

    private val offset = rect.position

    init {
        require(size <= backend.size) {
            "The size of a sub tile graphics can't be bigger than the original tile graphics."
        }
        require(offset.toSize() + size <= backend.size) {
            "sub tile graphics offset ($offset) is too big for size '$size'."
        }
    }

    override fun fetchFilledPositions(): List<Position> {
        return backend.createSnapshot().cells
                .filter { rect.containsPosition(it.position) }
                .map { it.position - offset }
    }

    override fun resize(newSize: Size) = restrictOperation()

    override fun resize(newSize: Size, filler: Tile) = restrictOperation()

    override fun applyStyle(styleSet: StyleSet, rect: Rect, keepModifiers: Boolean, applyToEmptyCells: Boolean) {
        super.applyStyle(
                styleSet = styleSet,
                // this is needed because I don't want to re-implement applyStyle...
                rect = rect.withPosition(Position.defaultPosition()),
                keepModifiers = keepModifiers,
                applyToEmptyCells = applyToEmptyCells)
    }

    override fun toTileImage(): TileImage {
        return DefaultTileImage(
                size = size,
                tileset = currentTileset(),
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

    override fun createSnapshot(): Snapshot {
        throw UnsupportedOperationException("Sub tile images don't support snapshots.")
    }

    override fun draw(drawable: Drawable, position: Position) {
        restrictOperation()
    }

    // TODO: test this
    override fun drawOnto(surface: DrawSurface, position: Position) {
        rect.size.fetchPositions().forEach { pos ->
            getTileAt(pos).map { tile ->
                surface.setTileAt(pos + offset + position, tile)
            }
        }
    }

    private fun restrictOperation(): Nothing {
        throw UnsupportedOperationException("This operation is not supported for sub tile graphics.")
    }

}
