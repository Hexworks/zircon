package org.hexworks.zircon.api.graphics.base

import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.cobalt.datatypes.extensions.map
import org.hexworks.zircon.api.behavior.Styleable
import org.hexworks.zircon.api.behavior.TilesetOverride
import org.hexworks.zircon.api.builder.data.TileBuilder
import org.hexworks.zircon.api.builder.graphics.TileGraphicsBuilder
import org.hexworks.zircon.api.data.Cell
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Rect
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Snapshot
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.extensions.toMap
import org.hexworks.zircon.api.graphics.DrawSurface
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.api.graphics.TileImage
import org.hexworks.zircon.api.graphics.impl.SubTileGraphics
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.util.TileTransformer
import org.hexworks.zircon.internal.behavior.impl.DefaultStyleable
import org.hexworks.zircon.internal.behavior.impl.DefaultTilesetOverride
import org.hexworks.zircon.internal.data.DefaultCell
import org.hexworks.zircon.internal.graphics.ConcurrentTileGraphics
import org.hexworks.zircon.internal.graphics.DefaultTileImage

/**
 * this is a basic building block which can be re-used by complex image
 * classes like layers, boxes, components, and more
 * all classes which are implementing the DrawSurface or the Drawable operations can
 * use this class as a base class just like how the TileGrid uses it
 */

abstract class BaseTileGraphics(
        styleSet: StyleSet,
        tileset: TilesetResource,
        initialSize: Size,
        private val tilesetOverride: TilesetOverride = DefaultTilesetOverride(
                tileset = tileset),
        private val contents: MutableMap<Position, Tile>,
        styleable: Styleable = DefaultStyleable(styleSet))
    : TileGraphics,
        Styleable by styleable,
        TilesetOverride by tilesetOverride {

    override val size = initialSize

    private val rect = Rect.create(size = initialSize)

    override fun toString(): String {
        return (0 until height).joinToString("") { y ->
            (0 until width).joinToString("") { x ->
                getTileAt(Position.create(x, y))
                        .get()
                        .asCharacterTile()
                        .orElse(Tile.defaultTile())
                        .character.toString()
            }.plus("\n")
        }.trim()
    }

    override fun clear() {
        contents.clear()
    }

    override fun getTileAt(position: Position): Maybe<Tile> {
        return if (rect.containsPosition(position)) {
            Maybe.of(contents[position] ?: Tile.empty())
        } else {
            Maybe.empty()
        }
    }

    override fun setTileAt(position: Position, tile: Tile) {
        if (size.containsPosition(position)) {
            contents[position]?.let { previous ->
                if (previous != tile) {
                    contents[position] = tile
                }
            } ?: run {
                contents[position] = tile
            }
        }
    }

    override fun transformTileAt(position: Position, tileTransformer: TileTransformer) {
        getTileAt(position).map { tile ->
            setTileAt(position, tileTransformer(tile))
        }
    }

    override fun createSnapshot(): Snapshot {
        return Snapshot.create(
                cells = contents.entries.map { (pos, tile) ->
                    Cell.create(pos, tile)
                },
                tileset = currentTileset())
    }

    override fun drawOnto(surface: DrawSurface, position: Position) {
        contents.entries.forEach { (pos, tile) ->
            surface.setTileAt(pos + position, tile)
        }
    }

    override fun fetchFilledPositions(): Iterable<Position> = createSnapshot().fetchPositions()

    override fun fetchFilledTiles(): Iterable<Tile> = createSnapshot().fetchTiles()

    override fun fetchCells(): Iterable<Cell> {
        return fetchCellsBy(Position.defaultPosition(), size)
    }

    override fun fetchCellsBy(offset: Position, size: Size): Iterable<Cell> {
        return size.fetchPositions()
                .map { it + offset }
                .map { DefaultCell(it, getTileAt(it).get()) }
    }

    override fun resize(newSize: Size): TileGraphics {
        return resize(newSize, Tile.empty())
    }

    override fun resize(newSize: Size, filler: Tile): TileGraphics {
        // TODO: returnThis same type, use factory for this
        val result = ConcurrentTileGraphics(
                size = newSize,
                styleSet = toStyleSet(),
                tileset = currentTileset())
        createSnapshot().cells.filter { (pos) -> newSize.containsPosition(pos) }
                .forEach { (pos, tc) ->
                    result.setTileAt(pos, tc)
                }
        if (filler != Tile.empty()) {
            newSize.fetchPositions().subtract(size.fetchPositions()).forEach {
                result.setTileAt(it, filler)
            }
        }
        return result
    }

    override fun fill(filler: Tile): TileGraphics {
        size.fetchPositions().filter { pos ->
            getTileAt(pos).map { it == Tile.empty() }.orElse(false)
        }.forEach { pos ->
            setTileAt(pos, filler)
        }
        return this
    }

    override fun putText(text: String, position: Position) {
        text.forEachIndexed { col, char ->
            setTileAt(position.withRelativeX(col), TileBuilder
                    .newBuilder()
                    .withStyleSet(toStyleSet())
                    .withCharacter(char)
                    .build())
        }
    }

    // TODO: test this
    override fun transform(transformer: TileTransformer) {
        fetchCells().forEach { (pos, tile) ->
            setTileAt(pos, transformer(tile))
        }
    }

    override fun toTileImage(): TileImage {
        return DefaultTileImage(
                size = size,
                tileset = currentTileset(),
                tiles = createSnapshot().cells.toMap())
    }

    override fun toSubTileGraphics(rect: Rect): SubTileGraphics {
        return SubTileGraphics(
                rect = rect,
                backend = this)
    }

    override fun createCopy(): TileGraphics {
        return TileGraphicsBuilder.newBuilder()
                .withSize(size)
                .withTileset(currentTileset())
                .withStyle(toStyleSet())
                .build().also {
                    it.draw(this)
                }
    }
}
