package org.hexworks.zircon.api.graphics.base

import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.cobalt.datatypes.extensions.map
import org.hexworks.zircon.api.behavior.Styleable
import org.hexworks.zircon.api.behavior.TilesetOverride
import org.hexworks.zircon.api.builder.data.TileBuilder
import org.hexworks.zircon.api.data.*
import org.hexworks.zircon.api.graphics.DrawSurface
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.api.graphics.TileImage
import org.hexworks.zircon.api.graphics.impl.SubTileGraphics
import org.hexworks.zircon.api.kotlin.toMap
import org.hexworks.zircon.api.resource.TilesetResource
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
        override val size: Size,
        private val tilesetOverride: TilesetOverride = DefaultTilesetOverride(
                tileset = tileset),
        private val contents: MutableMap<Position, Tile>,
        styleable: Styleable = DefaultStyleable(styleSet))
    : TileGraphics,
        Styleable by styleable,
        TilesetOverride by tilesetOverride {

    private val rect = Rect.create(size = size)

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
        if (position.x < width && position.y < height) {
            contents[position] = tile
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

    override fun toTileImage(): TileImage {
        return DefaultTileImage(
                size = size,
                tileset = currentTileset(),
                tiles = createSnapshot().cells.toMap())
    }

    /**
     * Creates a new [TileGraphics] which will use this one as the underlying subsystem.
     * Writing is restricted to the area denoted by `bounds` so if `bounds` consists
     * of Position(1, 1) and Size(2, 2), the resulting [TileGraphics] will have a size
     * of (2, 2) and writing to it will write to the original graphics' surface, offset
     * by Position(1, 1).
     */
    override fun toSubTileGraphics(rect: Rect): SubTileGraphics {
        return SubTileGraphics(
                rect = rect,
                backend = this)
    }
}
