package org.hexworks.zircon.api.graphics.extensions

import kotlinx.collections.immutable.persistentHashMapOf
import org.hexworks.zircon.api.behavior.extensions.height
import org.hexworks.zircon.api.behavior.extensions.width
import org.hexworks.zircon.api.builder.graphics.tileGraphics
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.extensions.containsPosition
import org.hexworks.zircon.api.data.extensions.fetchPositions
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.api.graphics.TileImage
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.graphics.DefaultTileImage
import kotlin.math.max

/**
 * Returns a new [TileImage] with the supplied [tile] set at the
 * given [position]. Has no effect if [position] is outside
 * this [TileImage]'s [size].
 */
fun TileImage.withTileAt(position: Position, tile: Tile): TileImage {
    return if (size.containsPosition(position)) {
        DefaultTileImage(
            size = size,
            tileset = tileset,
            initialTiles = tiles.put(position, tile)
        )
    } else {
        this
    }
}

/**
 * Returns a copy of this image resized to a new size.
 * The copy will be independent of the one this method is
 * invoked on, so modifying one will not affect the other.
 */
//! TODO: what happens if we shrink the TileImage?
fun TileImage.withNewSize(newSize: Size): TileImage {
    return withNewSize(newSize, Tile.empty())
}

/**
 * Returns a copy of this image resized to a new size and using
 * a specified filler [Tile] if the new size is larger than the old,
 * and we need to fill in empty areas.
 * The copy will be independent of the one this method is
 * invoked on, so modifying one will not affect the other.
 */
//! TODO: will this fill empty cells within its original size?
fun TileImage.withNewSize(newSize: Size, filler: Tile): TileImage {
    if (newSize == size) return this
    var newTiles = persistentHashMapOf<Position, Tile>()
    tiles.filterKeys { newSize.containsPosition(it) }
        .forEach { (pos, tile) ->
            newTiles = newTiles.put(pos, tile)
        }
    if (filler != Tile.empty()) {
        newSize.fetchPositions().subtract(size.fetchPositions().toSet()).forEach {
            newTiles = newTiles.put(it, filler)
        }
    }
    return DefaultTileImage(
        size = newSize,
        tileset = tileset,
        initialTiles = newTiles
    )
}

/**
 * Fills the empty parts of this [TileImage] with the given `filler`.
 */
fun TileImage.withFiller(filler: Tile): TileImage {
    if (filler == Tile.empty()) {
        return this
    }
    var newTiles = tiles
    size.fetchPositions().subtract(tiles.keys).forEach { pos ->
        newTiles = newTiles.put(pos, filler)
    }
    return DefaultTileImage(
        size = size,
        tileset = tileset,
        initialTiles = newTiles
    )
}

/**
 * Applies the given [StyleSet] to the [Tile]s in this [TileImage] and returns
 * a new [TileImage] with the result.
 */
fun TileImage.withStyle(styleSet: StyleSet): TileImage = transform {
    it.withStyle(styleSet)
}

/**
 * Creates a copy of this [TileImage] that uses the given [tileset].
 */
fun TileImage.withTileset(tileset: TilesetResource): TileImage {
    return DefaultTileImage(
        size = size,
        tileset = tileset,
        initialTiles = tiles
    )
}

/**
 * This method creates a new [TileImage] which is the combination of `this` one and the
 * supplied `tileImage`. If there are two [Position]s that are present in both [TileImage]s
 * **and** at none of those positions is an `EMPTY` [Tile] then the [Tile] in the supplied
 * `tileImage` will be used, e.g.: [tileImage] overwrites `this` image.
 *
 * The size of the new [TileImage] will be the size of the current [TileImage] UNLESS
 * the offset + `tileImage` would overflow.
 * In that case the new [TileImage] will be resized to fit [tileImage] accordingly.
 * The [TilesetResource] of the original [TileImage] will be used.
 * @param tileImage the image which will be drawn onto `this` image
 * @param offset The position on the target image where the `tileImage`'s top left corner will be
 */
//! TODO: why do we use the tileset of the original?
fun TileImage.combineWith(tileImage: TileImage, offset: Position): TileImage {
    val columns = max(width, offset.x + tileImage.width)
    val rows = max(height, offset.y + tileImage.height)
    val newSize = Size.create(columns, rows)

    var newTiles = tiles
    newTiles = newTiles.putAll(tileImage.tiles.mapKeys { it.key + offset })
    return DefaultTileImage(
        size = newSize,
        tileset = tileset,
        initialTiles = newTiles
    )
}

/**
 * Transforms all of the [Tile]s in this [TileImage] with the given
 * `transformer` and returns a new one with the transformed characters.
 */
fun TileImage.transform(transformer: (Tile) -> Tile): TileImage {
    var newTiles = tiles
    tiles.forEach { (pos, tile) ->
        newTiles = newTiles.put(pos, transformer.invoke(tile))
    }
    return DefaultTileImage(
        size = size,
        tileset = tileset,
        initialTiles = newTiles
    )
}

/**
 * Returns a part of this [TileImage] as a new [TileImage].
 * @param offset the position from which copying will start
 * @param size the size of the newly created image.
 * If the new image overflows, an exception is thrown
 */
fun TileImage.toSubImage(offset: Position, size: Size): TileImage {
    var newTiles = persistentHashMapOf<Position, Tile>()
    size.fetchPositions()
        .map { it + offset }
        .intersect(this.size.fetchPositions().toSet())
        .forEach { pos ->
            getTileAtOrNull(pos)?.let { tile ->
                newTiles = newTiles.put(pos - offset, tile)
            }
        }
    return DefaultTileImage(
        size = size,
        tileset = tileset,
        initialTiles = newTiles
    )
}

/**
 * Returns a copy of this [TileImage] as a [TileGraphics].
 */
fun TileImage.toTileGraphics(): TileGraphics = tileGraphics {
    size = this@toTileGraphics.size
    tileset = this@toTileGraphics.tileset
    tiles = this@toTileGraphics.tiles
}
