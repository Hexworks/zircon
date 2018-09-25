package org.hexworks.zircon.api.graphics

import org.hexworks.zircon.api.behavior.Boundable
import org.hexworks.zircon.api.behavior.DrawSurface
import org.hexworks.zircon.api.behavior.Drawable
import org.hexworks.zircon.api.behavior.TilesetOverride
import org.hexworks.zircon.api.builder.data.TileBuilder
import org.hexworks.zircon.api.builder.graphics.TileGraphicsBuilder
import org.hexworks.zircon.api.data.Cell
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.kotlin.map
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.util.Math
import org.hexworks.zircon.api.util.Maybe
import org.hexworks.zircon.api.util.TileTransformer
import org.hexworks.zircon.internal.data.DefaultCell
import org.hexworks.zircon.internal.graphics.DefaultTileImage

/**
 * An immutable image built from [Tile]s. It is completely in memory but it can be drawn onto
 * [DrawSurface]s like a [org.hexworks.zircon.api.grid.TileGrid] or a [TileGraphics].
 */
interface TileImage
    : Boundable, Drawable, TilesetOverride {

    /**
     * Returns the character stored at a particular position on this [DrawSurface].
     * Returns an empty [Maybe] if no [Tile] is present at the given [Position].
     */
    fun getTileAt(position: Position): Maybe<Tile>

    /**
     * Returns a [List] of [Position]s which are not considered empty.
     * @see [Tile.empty]
     */
    fun fetchFilledPositions(): List<Position>

    /**
     * Returns all the [Cell]s ([Tile]s with associated [Position] information)
     * of this [TileImage].
     */
    fun fetchCells(): Iterable<Cell> {
        return fetchCellsBy(Position.defaultPosition(), size())
    }

    /**
     * Returns the [Cell]s in this [TileImage] from the given `offset`
     * position and area.
     * Throws an exception if either `offset` or `size` would overlap
     * with this [TileImage].
     */
    fun fetchCellsBy(offset: Position, size: Size): Iterable<Cell> {
        return size.fetchPositions()
                .map { pos -> pos + offset }
                .map { pos -> DefaultCell(pos, getTileAt(pos).orElse(Tile.empty())) }
    }

    /**
     * Sets a [Tile] at a specific position in the [DrawSurface] to `tile`.
     * If the position is outside of the [DrawSurface]'s size, this method has no effect.
     * Note that if this [DrawSurface] already has the given [Tile] on the supplied [Position]
     * nothing will change.
     */
    fun withTileAt(position: Position, tile: Tile): TileImage {
        if (size().containsPosition(position)) {
            val tiles = toTileMap()
            val originalTile = getTileAt(position)
            if (originalTile.isPresent && originalTile.get() == tile) {
                return this
            }
            tiles[position] = tile
            return DefaultTileImage(
                    size = size(),
                    tileset = tileset(),
                    tiles = tiles.toMap())
        } else {
            return this
        }
    }

    /**
     * Returns a copy of this image resized to a new size and using
     * an empty [Tile] if the new size is larger than the old and
     * we need to fill in empty areas.
     * The copy will be independent from the one this method is
     * invoked on, so modifying one will not affect the other.
     */
    fun withNewSize(newSize: Size): TileImage {
        return withNewSize(newSize, Tile.empty())
    }

    /**
     * Returns a copy of this image resized to a new size and using
     * a specified filler [Tile] if the new size is larger than the old and
     * we need to fill in empty areas.
     * The copy will be independent from the one this method is
     * invoked on, so modifying one will not affect the other.
     */
    fun withNewSize(newSize: Size, filler: Tile): TileImage {
        val oldTiles = toTileMap()
        val tiles = mutableMapOf<Position, Tile>()
        oldTiles.filterKeys { newSize.containsPosition(it) }
                .forEach { (pos, tile) ->
                    tiles[pos] = tile
                }
        if (filler != Tile.empty()) {
            newSize.fetchPositions().subtract(size().fetchPositions()).forEach {
                tiles[it] = filler
            }
        }
        return DefaultTileImage(
                size = newSize,
                tileset = tileset(),
                tiles = tiles.toMap())
    }

    /**
     * Fills the empty parts of this [TileImage] with the given `filler`.
     */
    fun withFiller(filler: Tile): TileImage {
        if (filler == Tile.empty()) {
            return this
        }
        val tiles = toTileMap()
        size().fetchPositions().subtract(fetchFilledPositions()).forEach { pos ->
            tiles[pos] = filler
        }
        return DefaultTileImage(
                size = size(),
                tileset = tileset(),
                tiles = tiles.toMap())
    }

    /**
     * Returns a new [TileImage] with the given `text` written at the given `position`.
     */
    fun withText(text: String, style: StyleSet, position: Position = Position.defaultPosition()): TileImage {
        val tiles = toTileMap()
        text.forEachIndexed { col, char ->
            tiles[position.withRelativeX(col)] = TileBuilder
                    .newBuilder()
                    .styleSet(style)
                    .character(char)
                    .build()
        }
        return DefaultTileImage(
                size = size(),
                tileset = tileset(),
                tiles = tiles.toMap())
    }

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
                  size: Size = size(),
                  keepModifiers: Boolean = false): TileImage {
        val tiles = toTileMap()
        size.fetchPositions().forEach { pos ->
            pos.plus(offset).let { fixedPos ->
                getTileAt(fixedPos).map { tile: Tile ->
                    val oldMods = tile.styleSet().modifiers()
                    val newTile = if (keepModifiers) {
                        tile.withStyle(styleSet.withAddedModifiers(oldMods))
                    } else {
                        tile.withStyle(styleSet)
                    }
                    tiles[fixedPos] = newTile
                }
            }
        }
        return DefaultTileImage(
                size = size(),
                tileset = tileset(),
                tiles = tiles.toMap())
    }

    /**
     * Creates a copy of this [TileImage] which uses the given `tileset`.
     */
    fun withTileset(tileset: TilesetResource): TileImage {
        return DefaultTileImage(
                size = size(),
                tileset = tileset,
                tiles = toTileMap().toMap())
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
     * would overflow. In that case the new [TileImage] will be resized to fit the new TileGraphic accordingly.
     * The [org.hexworks.zircon.api.resource.TilesetResource] of the original [TileImage] will be used.
     * @param tileImage the image which will be drawn onto `this` image
     * @param offset The position on the target image where the `tileImage`'s top left corner will be
     */
    fun combineWith(tileImage: TileImage, offset: Position): TileImage {
        val columns = Math.max(size().xLength, offset.x + tileImage.size().xLength)
        val rows = Math.max(size().yLength, offset.y + tileImage.size().yLength)
        val newSize = Size.create(columns, rows)

        val tiles = toTileMap()
        tiles.putAll(tileImage.toTileMap().mapKeys { it.key + offset })
        return DefaultTileImage(
                size = newSize,
                tileset = tileset(),
                tiles = tiles.toMap())
    }

    /**
     * Transforms all of the [Tile]s in this [TileImage] with the given
     * `transformer` and returns a new one with the transformed characters.
     */
    fun transform(transformer: TileTransformer): TileImage {
        val tiles = mutableMapOf<Position, Tile>()
        fetchCells().forEach { (pos, tile) ->
            tiles[pos] = transformer.transform(tile)
        }
        return DefaultTileImage(
                size = size(),
                tileset = tileset(),
                tiles = tiles.toMap())
    }

    /**
     * Returns a part of this [TileImage] as a new [TileImage].
     * @param offset the position from which copying will start
     * @param size the size of the newly created image.
     * If the new image would overflow an exception is thrown
     */
    fun toSubImage(offset: Position, size: Size): TileImage {
        val tiles = mutableMapOf<Position, Tile>()
        size.fetchPositions()
                .map { it + offset }
                .intersect(size().fetchPositions())
                .forEach {
                    tiles[it - offset] = getTileAt(it).get()
                }
        return DefaultTileImage(
                size = size,
                tileset = tileset(),
                tiles = tiles.toMap())
    }

    /**
     * Returns the contents of this [TileImage] as a map of
     * [Position] - [Tile] pairs.
     */
    fun toTileMap(): MutableMap<Position, Tile> {
        val tileMap = mutableMapOf<Position, Tile>()
        fetchFilledPositions().forEach { pos ->
            getTileAt(pos).map { tile ->
                tileMap[pos] = tile
            }
        }
        return tileMap
    }

    /**
     * Returns a copy of this [TileImage] with the exact same content.
     */
    fun toTileImage(): TileImage = toSubImage(Position.defaultPosition(), size())

    /**
     * Returns a copy of this [TileImage] with the exact same content as a
     * [TileGraphics] which can be modified using the supplied style.
     */
    fun toTileGraphic(): TileGraphics {
        val result = TileGraphicsBuilder.newBuilder()
                .size(size())
                .tileset(tileset())
        toTileMap().forEach { (pos, tile) ->
            result.tile(pos, tile)
        }
        return result.build()
    }
}
