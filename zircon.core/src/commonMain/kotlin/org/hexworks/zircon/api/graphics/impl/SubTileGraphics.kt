package org.hexworks.zircon.api.graphics.impl

import org.hexworks.zircon.api.Tiles
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Rect
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.Tile.Companion
import org.hexworks.zircon.internal.data.TileGraphicsState
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.graphics.TileComposite
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.api.resource.TilesetResource

/**
 * Represents a sub-section of a [TileGraphics]. This class can be used to
 * restrict edits to the original [TileGraphics]. Note that the contents of
 * the two [TileGraphics] are shared so edits will be visible for both.
 */
@Suppress("OverridingDeprecatedMember")
class SubTileGraphics(
        private val rect: Rect,
        private val backend: TileGraphics)
    : TileGraphics {

    override val size = rect.size

    override val tiles: Map<Position, Tile>
        get() = state.tiles

    override val state: TileGraphicsState
        get() {
            val (tiles, tileset) = backend.state
            return TileGraphicsState.create(
                    tiles = tiles.filter {
                        rect.containsPosition(it.key)
                    }.map {
                        it.key - offset to it.value
                    }.toMap(), tileset = tileset, size = size)
        }

    override var tileset: TilesetResource
        get() = backend.tileset
        set(_) {
            restrictOperation()
        }

    private val offset = rect.position

    init {
        require(size <= backend.size) {
            "The size of a sub tile graphics can't be bigger than the original tile graphics."
        }
        require(offset.toSize() + size <= backend.size) {
            "sub tile graphics offset ($offset) and size ($size)" +
                    " is too big for backend size '${backend.size}'."
        }
    }

    override fun setTileAt(position: Position, tile: Tile) = draw(tile, position)

    override fun draw(tileMap: Map<Position, Tile>, drawPosition: Position, drawArea: Size) {
        backend.draw(tileMap, drawPosition + offset, drawArea)
    }

    override fun draw(tile: Tile, drawPosition: Position) {
        backend.draw(tile, drawPosition + offset)
    }

    override fun draw(tileComposite: TileComposite, drawPosition: Position, drawArea: Size) {
        draw(tileComposite.tiles, drawPosition, drawArea)
    }

    override fun draw(tileComposite: TileComposite) {
        draw(tileComposite.tiles, Position.defaultPosition(), size)
    }

    override fun draw(tileComposite: TileComposite, drawPosition: Position) {
        draw(tileComposite.tiles, drawPosition, size)
    }

    override fun draw(tileMap: Map<Position, Tile>) {
        draw(tileMap, Position.defaultPosition(), size)
    }

    override fun draw(tileMap: Map<Position, Tile>, drawPosition: Position) {
        draw(tileMap, drawPosition, size)
    }

    override fun transformTileAt(position: Position, tileTransformer: (Tile) -> Tile) {
        if (size.containsPosition(position)) {
            backend.transformTileAt(position + offset, tileTransformer)
        }
    }

    override fun fill(filler: Tile) {
        val (tiles, _, size) = state
        val result = mutableMapOf<Position, Tile>()
        size.fetchPositions()
                .minus(tiles.keys.filter { it != Tile.empty() })
                .forEach { emptyPos ->
                    result[emptyPos] = filler
                }
        draw(result)
    }

    override fun transform(transformer: (Position, Tile) -> Tile) {
        val result = mutableMapOf<Position, Tile>()
        state.tiles.forEach { (pos, tile) ->
            result[pos] = transformer(pos, tile)
        }
        draw(result)
    }

    override fun applyStyle(styleSet: StyleSet) {
        transform { _, tile -> tile.withStyle(styleSet) }
    }

    override fun clear() {
        size.fetchPositions().forEach {
            backend.draw(Tile.empty(), it + offset)
        }
    }

    override fun toSubTileGraphics(rect: Rect) = backend.toSubTileGraphics(
            Rect.create(
                    position = offset + rect.position,
                    size = size.min(rect.size)))


    // RESTRICTED OPERATIONS

    override fun createCopy() = restrictOperation()

    override fun toResized(newSize: Size) = restrictOperation()

    override fun toResized(newSize: Size, filler: Tile) = restrictOperation()

    override fun toTileImage() = restrictOperation()

    override fun toLayer(offset: Position) = restrictOperation()

    private fun restrictOperation(): Nothing {
        throw UnsupportedOperationException("This operation is not supported for sub tile graphics.")
    }

}
