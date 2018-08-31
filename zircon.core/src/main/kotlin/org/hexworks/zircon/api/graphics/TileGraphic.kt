package org.hexworks.zircon.api.graphics

import org.hexworks.zircon.api.behavior.*
import org.hexworks.zircon.api.builder.data.TileBuilder
import org.hexworks.zircon.api.data.Cell
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.internal.data.DefaultCell
import org.hexworks.zircon.internal.graphics.ConcurrentTileGraphic
import org.hexworks.zircon.internal.graphics.DefaultTileImage

/**
 * An image built from [Tile]s with color and style information.
 * It is completely in memory but it can be drawn onto other
 * [DrawSurface]s like a [org.hexworks.zircon.api.grid.TileGrid].
 */
interface TileGraphic
    : Clearable, DrawSurface, Drawable, Styleable, TilesetOverride {

    /**
     * Returns a [List] of [Position]s which are not `EMPTY`.
     */
    fun fetchFilledPositions(): List<Position> = snapshot().keys.toList()

    /**
     * Returns a copy of this image resized to a new size and using
     * a specified filler [Tile] if the new size is larger than the old and
     * we need to fill in empty areas.
     * The copy will be independent from the one this method is
     * invoked on, so modifying one will not affect the other.
     */
    fun resize(newSize: Size, filler: Tile): TileGraphic {
        // TODO: return same type, use factory for this
        val result = ConcurrentTileGraphic(
                size = newSize,
                styleSet = styleSet(),
                tileset = tileset())
        snapshot().filter { (pos) -> newSize.containsPosition(pos) }
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
     * Returns a copy of this image resized to a new size and using
     * an empty [Tile] if the new size is larger than the old and
     * we need to fill in empty areas.
     * The copy will be independent from the one this method is
     * invoked on, so modifying one will not affect the other.
     */
    fun resize(newSize: Size): TileGraphic {
        return resize(newSize, Tile.empty())
    }

    /**
     * Fills the empty parts of this [TileGraphic] with the given `filler`.
     */
    fun fill(filler: Tile): TileGraphic {
        size().fetchPositions().filter { pos ->
            getTileAt(pos).map { it == Tile.empty() }.orElse(false)
        }.forEach { pos ->
            setTileAt(pos, filler)
        }
        return this
    }

    /**
     * Returns all the [Cell]s ([Tile]s with associated [Position] information)
     * of this [TileGraphic].
     */
    fun fetchCells(): Iterable<Cell> {
        return fetchCellsBy(Position.defaultPosition(), size())
    }

    /**
     * Returns the [Cell]s in this [TileGraphic] from the given `offset`
     * position and area.
     * Throws an exception if either `offset` or `size` would overlap
     * with this [TileGraphic].
     */
    fun fetchCellsBy(offset: Position, size: Size): Iterable<Cell> {
        return size.fetchPositions()
                .map { it + offset }
                .map { DefaultCell(it, getTileAt(it).get()) }
    }

    /**
     * Writes the given `text` at the given `position`.
     */
    fun putText(text: String, position: Position = Position.defaultPosition()) {
        text.forEachIndexed { col, char ->
            setTileAt(position.withRelativeX(col), TileBuilder
                    .newBuilder()
                    .styleSet(styleSet())
                    .character(char)
                    .build())
        }
    }

    /**
     * Sets the style of this [TileGraphic] from the given `styleSet`
     * and also applies it to all currently present
     * [Tile]s within the bounds delimited by `offset` and `size`.
     * Offset is used to offset the starting position from the top left position
     * while size is used to determine the region (down and right) to overwrite
     * relative to `offset`.
     * @param keepModifiers whether the modifiers currently present in the
     * target [Tile]s should be kept or not
     */
    fun applyStyle(styleSet: StyleSet,
                   offset: Position = Position.defaultPosition(),
                   size: Size = size(),
                   keepModifiers: Boolean = false) {
        setStyleFrom(styleSet)
        size.fetchPositions().forEach { pos ->
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
                tileset = tileset(),
                tiles = snapshot().toMap())
    }
}
