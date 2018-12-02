package org.hexworks.zircon.api.graphics

import org.hexworks.cobalt.datatypes.extensions.map
import org.hexworks.zircon.api.behavior.Clearable
import org.hexworks.zircon.api.behavior.Drawable
import org.hexworks.zircon.api.behavior.Styleable
import org.hexworks.zircon.api.data.*
import org.hexworks.zircon.api.graphics.impl.SubTileGraphics

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
    fun fetchFilledPositions(): Iterable<Position>

    /**
     * Returns a [List] of [Tile]s which are not `EMPTY`.
     */
    fun fetchFilledTiles(): Iterable<Tile>

    /**
     * Returns all the [Cell]s ([Tile]s with associated [Position] information)
     * of this [TileGraphics].
     */
    fun fetchCells(): Iterable<Cell>

    /**
     * Returns the [Cell]s in this [TileGraphics] from the given `offset`
     * position and area.
     * Throws an exception if either `offset` or `size` would overlap
     * with this [TileGraphics].
     */
    fun fetchCellsBy(offset: Position, size: Size): Iterable<Cell>

    /**
     * Returns a copy of this image resized to a new size and using
     * an empty [Tile] if the new size is larger than the old and
     * we need to fill in empty areas.
     * The copy will be independent from the one this method is
     * invoked on, so modifying one will not affect the other.
     */
    fun resize(newSize: Size): TileGraphics

    /**
     * Returns a copy of this image resized to a new size and using
     * a specified filler [Tile] if the new size is larger than the old and
     * we need to fill in empty areas.
     * The copy will be independent from the one this method is
     * invoked on, so modifying one will not affect the other.
     */
    fun resize(newSize: Size, filler: Tile): TileGraphics

    /**
     * Fills the empty parts of this [TileGraphics] with the given `filler`.
     */
    fun fill(filler: Tile): TileGraphics

    /**
     * Writes the given `text` at the given `position`.
     */
    fun putText(text: String, position: Position = Position.zero())

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
                   rect: Rect = Rect.create(size = this.size),
                   keepModifiers: Boolean = false,
                   applyToEmptyCells: Boolean = true) {
        val offset = rect.position
        val size = rect.size
        setStyleFrom(styleSet)
        val positions = if (applyToEmptyCells) {
            size.fetchPositions()
        } else {
            fetchFilledPositions()
        }
        positions.forEach { pos ->
            pos.plus(offset).let { fixedPos ->
                getTileAt(fixedPos).map { tile: Tile ->
                    val oldMods = tile.styleSet.modifiers
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

    /**
     * Converts this [TileGraphics] to a new [TileImage].
     */
    fun toTileImage(): TileImage

    /**
     * Creates a new [TileGraphics] which will use this one as the underlying subsystem.
     * Writing is restricted to the area denoted by `bounds` so if `bounds` consists
     * of Position(1, 1) and Size(2, 2), the resulting [TileGraphics] will have a size
     * of (2, 2) and writing to it will write to the original graphics' surface, offset
     * by Position(1, 1).
     */
    fun toSubTileGraphics(rect: Rect): SubTileGraphics

    /**
     * Creates a copy of this [TileGraphics].
     */
    fun createCopy(): TileGraphics
}
