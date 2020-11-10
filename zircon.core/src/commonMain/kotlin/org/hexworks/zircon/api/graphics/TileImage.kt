package org.hexworks.zircon.api.graphics

import kotlinx.collections.immutable.PersistentMap
import org.hexworks.zircon.api.behavior.TilesetHolder
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.resource.TilesetResource

/**
 * An immutable [TileComposite]. It is completely in memory but it can be drawn onto
 * [TileGraphics]s and its derivatives. Also supports operations for combining with
 * other [TileImage]s and converting to [TileGraphics].
 */
// TODO: test implementors thoroughly
interface TileImage : TileComposite, TilesetHolder {

    /**
     * The [Tile]s this [TileComposite] contains. Note that a [TileImage]
     * uses a [PersistentMap] to store the tiles to enable fast creation
     * of new [TileImage]s.
     */
    override val tiles: PersistentMap<Position, Tile>

    /**
     * Returns a new [TileImage] with the supplied [tile] set at the
     * given [position]. Has no effect if [position] is outside of
     * this [TileImage]'s [size].
     */
    fun withTileAt(position: Position, tile: Tile): TileImage

    /**
     * Returns a copy of this image resized to a new size.
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
     * Applies the given [StyleSet] to the [Tile]s in this [TileImage] and returns
     * a new [TileImage] with the result.
     */
    fun withStyle(styleSet: StyleSet): TileImage

    /**
     * Creates a copy of this [TileImage] which uses the given [tileset].
     */
    fun withTileset(tileset: TilesetResource): TileImage

    /**
     * This method creates a new [TileImage] which is the combination of `this` one and the
     * supplied `tileImage`. If there are two [Position]s which are present in both [TileImage]s
     * **and** at none of those positions is an `EMPTY` [Tile] then the [Tile] in the supplied
     * `tileImage` will be used, eg: [tileImage] overwrites `this` image.
     *
     * The size of the new [TileImage] will be the size of the current [TileImage] UNLESS the offset + `tileImage`
     * would overflow. In that case the new [TileImage] will be resized to fit [tileImage] accordingly.
     * The [TilesetResource] of the original [TileImage] will be used.
     * @param tileImage the image which will be drawn onto `this` image
     * @param offset The position on the target image where the `tileImage`'s top left corner will be
     */
    fun combineWith(tileImage: TileImage, offset: Position): TileImage

    /**
     * Transforms all of the [Tile]s in this [TileImage] with the given
     * `transformer` and returns a new one with the transformed characters.
     */
    fun transform(transformer: (Tile) -> Tile): TileImage

    /**
     * Returns a part of this [TileImage] as a new [TileImage].
     * @param offset the position from which copying will start
     * @param size the size of the newly created image.
     * If the new image would overflow an exception is thrown
     */
    fun toSubImage(offset: Position, size: Size): TileImage

    /**
     * Returns a copy of this [TileImage] as a [TileGraphics].
     */
    fun toTileGraphics(): TileGraphics
}
