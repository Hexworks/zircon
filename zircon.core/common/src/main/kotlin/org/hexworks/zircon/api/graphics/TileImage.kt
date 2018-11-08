package org.hexworks.zircon.api.graphics

import org.hexworks.zircon.api.behavior.Drawable
import org.hexworks.zircon.api.behavior.TilesetOverride
import org.hexworks.zircon.api.data.Cell
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.util.TileTransformer

/**
 * An immutable image built from [Tile]s. It is completely in memory but it can be drawn onto
 * [DrawSurface]s like a [org.hexworks.zircon.api.grid.TileGrid] or a [TileGraphics].
 */
interface TileImage : Drawable, TileComposite, TilesetOverride {


    /**
     * Returns a [List] of [Position]s which are not considered empty.
     * @see [Tile.empty]
     */
    fun fetchFilledPositions(): List<Position>

    /**
     * Returns all the [Cell]s ([Tile]s with associated [Position] information)
     * of this [TileImage].
     */
    fun fetchCells(): Iterable<Cell>

    /**
     * Returns the [Cell]s in this [TileImage] from the given `offset`
     * position and area.
     * Throws an exception if either `offset` or `size` would overlap
     * with this [TileImage].
     */
    fun fetchCellsBy(offset: Position, size: Size): Iterable<Cell>

    /**
     * Sets a [Tile] at a specific position in the [DrawSurface] to `tile`.
     * If the position is outside of the [DrawSurface]'s size, this method has no effect.
     * Note that if this [DrawSurface] already has the given [Tile] on the supplied [Position]
     * nothing will change.
     */
    fun withTileAt(position: Position, tile: Tile): TileImage

    /**
     * Returns a copy of this image resized to a new size and using
     * an empty [Tile] if the new size is larger than the old and
     * we need to fill in empty areas.
     * The copy will be independent from the one this method is
     * invoked on, so modifying one will not affect the other.
     */
    fun withNewSize(newSize: Size): TileImage

    /**
     * Returns a copy of this image resized to a new size and using
     * a specified filler [Tile] if the new size is larger than the old and
     * we need to fill in empty areas.
     * The copy will be independent from the one this method is
     * invoked on, so modifying one will not affect the other.
     */
    fun withNewSize(newSize: Size, filler: Tile): TileImage

    /**
     * Fills the empty parts of this [TileImage] with the given `filler`.
     */
    fun withFiller(filler: Tile): TileImage

    /**
     * Returns a new [TileImage] with the given `text` written at the given `position`.
     */
    fun withText(text: String, style: StyleSet, position: Position = Position.defaultPosition()): TileImage

    /**
     * Applies the given [StyleSet] to the [Tile]s in this [TileImage] and returns
     * a new [TileImage] with the result.
     * Only the [Tile]s within the bounds delimited by `offset` and `size` will be
     * transformed.
     * Offset is used to offset the starting position from the top left position
     * while size is used to determine the region (down and right) to overwrite
     * relative to `offset`.
     * @param keepModifiers whether the modifiers currently present in the
     * target [Tile]s should be kept or not
     */
    fun withStyle(styleSet: StyleSet,
                  offset: Position = Position.defaultPosition(),
                  size: Size = this.size,
                  keepModifiers: Boolean = false): TileImage

    /**
     * Creates a copy of this [TileImage] which uses the given `tileset`.
     */
    fun withTileset(tileset: TilesetResource): TileImage

    /**
     * Combines this text image with another one. This method creates a new
     * [TileImage] which is the combination of `this` one and the supplied `tileImage`.
     * *Note that* if there are two [Position]s which are present in both [TileImage]s
     * **and** at none of those positions is an `EMPTY` [Tile] then the
     * [Tile] in the supplied `tileImage` will be used.
     * This method creates a new object and **both** original [TileImage]s are left
     * untouched!
     * The size of the new [TileImage] will be the size of the current [TileImage] UNLESS the offset + `tileImage`
     * would overflow. In that case the new [TileImage] will be resized to fit the new TileGraphic accordingly.
     * The [org.hexworks.zircon.api.resource.TilesetResource] of the original [TileImage] will be used.
     * @param tileImage the image which will be drawn onto `this` image
     * @param offset The position on the target image where the `tileImage`'s top left corner will be
     */
    fun combineWith(tileImage: TileImage, offset: Position): TileImage

    /**
     * Transforms all of the [Tile]s in this [TileImage] with the given
     * `transformer` and returns a new one with the transformed characters.
     */
    fun transform(transformer: TileTransformer): TileImage

    /**
     * Returns a part of this [TileImage] as a new [TileImage].
     * @param offset the position from which copying will start
     * @param size the size of the newly created image.
     * If the new image would overflow an exception is thrown
     */
    fun toSubImage(offset: Position, size: Size): TileImage

    /**
     * Returns the contents of this [TileImage] as a map of
     * [Position] - [Tile] pairs.
     */
    fun toTileMap(): MutableMap<Position, Tile>

    /**
     * Returns a copy of this [TileImage] with the exact same content.
     */
    fun toTileImage(): TileImage

    /**
     * Returns a copy of this [TileImage] with the exact same content as a
     * [TileGraphics] which can be modified using the supplied style.
     */
    fun toTileGraphic(): TileGraphics
}
