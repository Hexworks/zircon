package org.hexworks.zircon.api.graphics.impl

import org.hexworks.zircon.api.behavior.Drawable
import org.hexworks.zircon.api.behavior.TilesetOverride
import org.hexworks.zircon.api.data.DrawSurfaceSnapshot
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Rect
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.extensions.toTileImage
import org.hexworks.zircon.api.graphics.DrawSurface
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.internal.behavior.impl.DefaultTilesetOverride

/**
 * Represents a sub-section of a [TileGraphics]. This class can be used to
 * restrict edits to the original [TileGraphics]. Note that the contents of
 * the two [TileGraphics] are shared so edits will be visible for both.
 */
class SubTileGraphics(
        private val rect: Rect,
        private val backend: TileGraphics,
        private val tilesetOverride: TilesetOverride = DefaultTilesetOverride(
                tileset = backend.currentTileset()))
    : TileGraphics,
        TilesetOverride by tilesetOverride {

    override val size = rect.size
    override val tiles: Map<Position, Tile>
        get() = backend.tiles.filter {
            size.containsPosition(it.key)
        }

    private val offset = rect.position

    init {
        require(size <= backend.size) {
            "The size of a sub tile graphics can't be bigger than the original tile graphics."
        }
        require(offset.toSize() + size <= backend.size) {
            "sub tile graphics offset ($offset) is too big for size '$size'."
        }
    }

    override fun fill(filler: Tile) {
        val (tiles, tileset, size) = createSnapshot()
        size.fetchPositions().minus(tiles.keys).map {
            it to filler
        }.toTileImage(size, tileset).drawOnto(this)
    }

    override fun toSubTileGraphics(rect: Rect) = SubTileGraphics(
            rect = rect,
            backend = backend)

    override fun applyStyle(styleSet: StyleSet, rect: Rect, keepModifiers: Boolean, applyToEmptyCells: Boolean) {
        super.applyStyle(
                styleSet = styleSet,
                // this is needed because I don't want to re-implement applyStyle...
                // TODO: why is this?
                rect = rect.withPosition(Position.defaultPosition()),
                keepModifiers = keepModifiers,
                applyToEmptyCells = applyToEmptyCells)
    }

    // TODO: optimize this
    override fun clear() {
        size.fetchPositions().forEach {
            backend.setTileAt(it + offset, Tile.empty())
        }
    }

    override fun setTileAt(position: Position, tile: Tile) {
        if (size.containsPosition(position)) {
            backend.setTileAt(position + offset, tile)
        }
    }

    override fun transformTileAt(position: Position, tileTransformer: (Tile) -> Tile) {
        if (size.containsPosition(position)) {
            backend.transformTileAt(position + offset, tileTransformer)
        }
    }

    override fun drawOnto(surface: DrawSurface, position: Position) {
        val (tiles, tileset, size) = createSnapshot()
        tiles.toTileImage(size, tileset).drawOnto(surface)
    }

    override fun createSnapshot(): DrawSurfaceSnapshot {
        val (tiles, tileset) = backend.createSnapshot()
        return DrawSurfaceSnapshot.create(
                tiles = tiles.filter {
                    size.containsPosition(it.key)
                }, tileset = tileset, size = size)
    }

    // TODO: why restrict this?
    override fun draw(drawable: Drawable, position: Position) {
        restrictOperation()
    }

    override fun createCopy() = restrictOperation()

    override fun resize(newSize: Size) = restrictOperation()

    override fun resize(newSize: Size, filler: Tile) = restrictOperation()

    private fun restrictOperation(): Nothing {
        throw UnsupportedOperationException("This operation is not supported for sub tile graphics.")
    }

}
