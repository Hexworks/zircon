package org.hexworks.zircon.api.graphics

import org.hexworks.zircon.api.behavior.Clearable
import org.hexworks.zircon.api.behavior.DrawSurface
import org.hexworks.zircon.api.behavior.Drawable
import org.hexworks.zircon.api.behavior.Styleable
import org.hexworks.zircon.api.builder.data.TileBuilder
import org.hexworks.zircon.api.data.*
import org.hexworks.zircon.api.kotlin.map
import org.hexworks.zircon.internal.data.DefaultCell
import org.hexworks.zircon.internal.graphics.ConcurrentTileGraphics
import org.hexworks.zircon.internal.graphics.DefaultTileImage

/**
 * An image built from [Tile]s with color and style information.
 * It is completely in memory but it can be drawn onto other
 * [DrawSurface]s like a [org.hexworks.zircon.api.grid.TileGrid].
 */
interface TileGraphics
    : Clearable, DrawSurface, Drawable, Styleable {

    /**
     * Returns a [List] of [Position]s which are not `EMPTY`.
     */
    fun fetchFilledPositions(): List<Position> = createSnapshot().keys.toList()

    /**
     * Returns a [List] of [Tile]s which are not `EMPTY`.
     */
    fun fetchFilledTiles(): List<Tile> = createSnapshot().values.toList()

    /**
     * Returns all the [Cell]s ([Tile]s with associated [Position] information)
     * of this [TileGraphics].
     */
    fun fetchCells(): Iterable<Cell> {
        return fetchCellsBy(Position.defaultPosition(), size())
    }

    /**
     * Returns the [Cell]s in this [TileGraphics] from the given `offset`
     * position and area.
     * Throws an exception if either `offset` or `size` would overlap
     * with this [TileGraphics].
     */
    fun fetchCellsBy(offset: Position, size: Size): Iterable<Cell> {
        return size.fetchPositions()
                .map { it + offset }
                .map { DefaultCell(it, getTileAt(it).get()) }
    }

    /**
     * Returns a copy of this image resized to a new size and using
     * an empty [Tile] if the new size is larger than the old and
     * we need to fill in empty areas.
     * The copy will be independent from the one this method is
     * invoked on, so modifying one will not affect the other.
     */
    fun resize(newSize: Size): TileGraphics {
        return resize(newSize, Tile.empty())
    }

    /**
     * Returns a copy of this image resized to a new size and using
     * a specified filler [Tile] if the new size is larger than the old and
     * we need to fill in empty areas.
     * The copy will be independent from the one this method is
     * invoked on, so modifying one will not affect the other.
     */
    fun resize(newSize: Size, filler: Tile): TileGraphics {
        // TODO: returnThis same type, use factory for this
        val result = ConcurrentTileGraphics(
                size = newSize,
                styleSet = toStyleSet(),
                tileset = currentTileset())
        createSnapshot().filter { (pos) -> newSize.containsPosition(pos) }
                .forEach { (pos, tc) ->
                    result.setTileAt(pos, tc)
                }
        if (filler != Tile.empty()) {
            newSize.fetchPositions().subtract(size().fetchPositions()).forEach {
                result.setTileAt(it, filler)
            }
        }
        return result
    }

    /**
     * Fills the empty parts of this [TileGraphics] with the given `filler`.
     */
    fun fill(filler: Tile): TileGraphics {
        size().fetchPositions().filter { pos ->
            getTileAt(pos).map { it == Tile.empty() }.orElse(false)
        }.forEach { pos ->
            setTileAt(pos, filler)
        }
        return this
    }

    /**
     * Writes the given `text` at the given `position`.
     */
    fun putText(text: String, position: Position = Position.defaultPosition()) {
        text.forEachIndexed { col, char ->
            setTileAt(position.withRelativeX(col), TileBuilder
                    .newBuilder()
                    .styleSet(toStyleSet())
                    .character(char)
                    .build())
        }
    }

    /**
     * Sets the style of this [TileGraphics] from the given `styleSet`
     * and also applies it to all currently present
     * [Tile]s within the bounds delimited by `offset` and `size`.
     * Offset is used to offset the starting position from the top left position
     * while size is used to determine the region (down and right) to overwrite
     * relative to `offset`.
     * @param keepModifiers whether the modifiers currently present in the
     * target [Tile]s should be kept or not
     */
    fun applyStyle(styleSet: StyleSet,
                   bounds: Bounds = bounds(),
                   keepModifiers: Boolean = false,
                   applyToEmptyCells: Boolean = true) {
        val offset = bounds.position()
        val size = bounds.size()
        setStyleFrom(styleSet)
        val positions = if (applyToEmptyCells) {
            size.fetchPositions()
        } else {
            fetchFilledPositions()
        }
        positions.forEach { pos ->
            pos.plus(offset).let { fixedPos ->
                getTileAt(fixedPos).map { tile: Tile ->
                    val oldMods = tile.styleSet().modifiers()
                    val newTile = if (keepModifiers) {
                        tile.withStyle(styleSet.withAddedModifiers(oldMods))
                    } else {
                        tile.withStyle(styleSet)
                    }
                    setTileAt(fixedPos, newTile)
                }
            }
        }
    }

    fun toTileImage(): TileImage {
        return DefaultTileImage(
                size = size(),
                tileset = currentTileset(),
                tiles = createSnapshot().toMap())
    }

    /**
     * Creates a new [TileGraphics] which will use this one as the underlying subsystem.
     * Writing is restricted to the area denoted by `bounds` so if `bounds` consists
     * of Position(1, 1) and Size(2, 2), the resulting [TileGraphics] will have a size
     * of (2, 2) and writing to it will write to the original graphics' surface, offset
     * by Position(1, 1).
     */
    fun toSubTileGraphics(bounds: Bounds): SubTileGraphics {
        return SubTileGraphics(this, bounds)
    }
}
