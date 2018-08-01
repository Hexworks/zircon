package org.codetome.zircon.api.graphics

import org.codetome.zircon.api.behavior.*
import org.codetome.zircon.api.behavior.TilesetOverride
import org.codetome.zircon.api.data.Cell
import org.codetome.zircon.api.data.Position
import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.sam.TextCharacterTransformer
import org.codetome.zircon.api.util.Math
import org.codetome.zircon.internal.graphics.ConcurrentTileImage

/**
 * An image built from [Tile]s with color and style information.
 * It is completely in memory but it can be drawn onto other
 * [DrawSurface]s like a [org.codetome.zircon.api.grid.TileGrid].
 */
interface TileImage<T : Any, S: Any>
    : Clearable, DrawSurface<T>, Drawable<T>, Styleable, TilesetOverride<T, S> {

    /**
     * Returns a [List] of [Position]s which are not `EMPTY`.
     */
    fun fetchFilledPositions(): List<Position> = createSnapshot().keys.toList()

    /**
     * Returns a copy of this [TileImage] with the exact same content.
     */
    fun copyImage(): TileImage<T, S> = toSubImage(Position.defaultPosition(), getBoundableSize())

    /**
     * Returns a part of this [TileImage] as a new [TileImage].
     * @param offset the position from which copying will start
     * @param size the size of the newly created image.
     * If the new image would overflow an exception is thrown
     */
    fun toSubImage(offset: Position, size: Size): TileImage<T, S> {
        val result = ConcurrentTileImage(getBoundableSize(), tileset(), toStyleSet())
        size.fetchPositions()
                .map { it + offset }
                .intersect(getBoundableSize().fetchPositions())
                .forEach {
                    result.setTileAt(it - offset, getTileAt(it).get())
                }
        return result
    }
    /**
     * Returns a copy of this image resized to a new size and using
     * a specified filler [Tile] if the new size is larger than the old and
     * we need to fill in empty areas.
     * The copy will be independent from the one this method is
     * invoked on, so modifying one will not affect the other.
     */
    fun resize(newSize: Size, filler: Tile<T>): TileImage<T, S> {
        // TODO: return same type, use factory for this
        val result = ConcurrentTileImage(
                size = newSize,
                styleSet = toStyleSet(),
                tileset = tileset())
        createSnapshot().filter { (pos) -> newSize.containsPosition(pos) }
                .forEach { (pos, tc) ->
                    result.setTileAt(pos, tc)
                }
        if(filler != Tile.empty()) {
            newSize.fetchPositions().subtract(getBoundableSize().fetchPositions()).forEach {
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
    fun resize(newSize: Size): TileImage<T, S> {
//        return resize(newSize, Tile.empty())
        TODO("Fix with Tile.empty")
    }

    /**
     * Fills the empty parts of this [TileImage] with the given `filler`.
     */
    fun fill(filler: Tile<T>): TileImage<T, S> {
        getBoundableSize().fetchPositions().filter { pos ->
            getTileAt(pos).map { it == Tile.empty() }.orElse(false)
        }.forEach { pos ->
            setTileAt(pos, filler)
        }
        return this
    }

    /**
     * Returns all the [Cell]s ([Tile]s with associated [Position] information)
     * of this [TileImage].
     */
    fun fetchCells(): Iterable<Cell<T>> {
        return fetchCellsBy(Position.defaultPosition(), getBoundableSize())
    }

    /**
     * Returns the [Cell]s in this [TileImage] from the given `offset`
     * position and area.
     * Throws an exception if either `offset` or `size` would overlap
     * with this [TileImage].
     */
    fun fetchCellsBy(offset: Position, size: Size): Iterable<Cell<T>> {
        return size.fetchPositions()
                .map { it + offset }
                .map { Cell(it, getTileAt(it).get()) }
    }

    /**
     * Combines this text image with another one. This method creates a new
     * [TileImage] which is the combination of `this` one and the supplied `tileImage`.
     * *Note that* if there are two [Position]s which are present in both [TileImage]s
     * **and** at none of those positions is an `EMPTY` [Tile] then the
     * [Tile] in the supplied `tileImage` will be used.
     * This method creates a new object and **both** original [TileImage]s are left
     * untouched!
     * The size of the new [TileImage] will be the size of the current [TileImage] UNLESS the offset + `tileImage`
     * would overflow. In that case the new [TileImage] will be resized to fit the new TileImage accordingly
     * @param tileImage the image which will be drawn onto `this` image
     * @param offset The position on the target image where the `tileImage`'s top left corner will be
     */
    fun combineWith(tileImage: TileImage<T, S>, offset: Position): TileImage<T, S> {
        val columns = Math.max(getBoundableSize().xLength, offset.x + tileImage.getBoundableSize().xLength)
        val rows = Math.max(getBoundableSize().yLength, offset.y + tileImage.getBoundableSize().yLength)

        val surface = resize(Size.create(columns, rows))
        surface.draw(tileImage, offset)
        return surface
    }

    /**
     * Transforms all of the [Tile]s in this [TileImage] with the given
     * `transformer` and returns a new one with the transformed characters.
     */
    fun transform(transformer: TextCharacterTransformer<T>): TileImage<T, S> {
        val result = ConcurrentTileImage(
                size = getBoundableSize(),
                tileset = tileset(),
                styleSet = toStyleSet())
        fetchCells().forEach { (pos, char) ->
            result.setTileAt(pos, transformer.transform(char))
        }
        return result
    }

    /**
     * Sets the style of this [TileImage] from the given `styleSet`
     * and also applies it to all currently present
     * [Tile]s within the bounds delimited by `offset` and `size`.
     * Offset is used to offset the starting position from the top left position
     * while size is used to determine the region (down and right) to overwrite
     * relative to `offset`.
     */
    fun applyStyle(styleSet: StyleSet,
                   offset: Position = Position.defaultPosition(),
                   size: Size = getBoundableSize()) {
        setStyleFrom(styleSet)
        size.fetchPositions().forEach { pos ->
            pos.plus(offset).let { fixedPos ->
                getTileAt(fixedPos).map { char: Tile<T> ->
                    setTileAt(fixedPos, char.withStyle(styleSet))
                }
            }
        }
    }
}
