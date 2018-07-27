package org.codetome.zircon.api.graphics

import org.codetome.zircon.api.behavior.Boundable
import org.codetome.zircon.api.behavior.DrawSurface
import org.codetome.zircon.api.behavior.Drawable
import org.codetome.zircon.api.behavior.Styleable
import org.codetome.zircon.api.data.Cell
import org.codetome.zircon.api.data.Position
import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.sam.TextCharacterTransformer

/**
 * An image built from [Tile]s with color and style information.
 * These are completely in memory and not visible,
 * but can be used when drawing on other [DrawSurface]s.
 */
interface TileImage : DrawSurface, Drawable, Styleable, Boundable {

    /**
     * Returns a [List] of [Position]s which are not `EMPTY`.
     */
    fun fetchFilledPositions(): List<Position>

    /**
     * Returns a copy of this [TileImage] with the exact same content.
     */
    fun copyImage(): TileImage = toSubImage(Position.defaultPosition(), getBoundableSize())

    /**
     * Returns a part of this [TileImage] as a new [TileImage].
     * @param offset the position from which copying will start
     * @param size the size of the newly created image.
     * If the new image would overflow an exception is thrown
     */
    fun toSubImage(offset: Position, size: Size): TileImage

    /**
     * Returns a copy of this image resized to a new size and using a specified filler character
     * if the new size is larger than the old and we need to fill in empty areas.
     * The copy will be independent from the one this method is
     * invoked on, so modifying one will not affect the other.
     */
    fun resize(newSize: Size, filler: Tile = Tile.empty()): TileImage

    /**
     * Fills the emtpy parts of this [TileImage] with the given `filler`.
     */
    fun fill(filler: Tile): TileImage

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
    fun combineWith(tileImage: TileImage, offset: Position): TileImage

    /**
     * Transforms all of the [Tile]s in this [TileImage] with the given
     * `transformer` and returns a new one with the transformed characters.
     */
    fun transform(transformer: TextCharacterTransformer): TileImage

    /**
     * Writes the given `text` at the given `position`.
     */
    fun putText(text: String, position: Position = Position.defaultPosition())

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
                   size: Size = getBoundableSize())


}
