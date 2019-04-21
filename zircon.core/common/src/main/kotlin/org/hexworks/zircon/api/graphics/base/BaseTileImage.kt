package org.hexworks.zircon.api.graphics.base

import org.hexworks.cobalt.datatypes.extensions.map
import org.hexworks.zircon.api.builder.data.TileBuilder
import org.hexworks.zircon.api.builder.graphics.TileGraphicsBuilder
import org.hexworks.zircon.api.data.Cell
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.api.graphics.TileImage
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.util.TileTransformer
import org.hexworks.zircon.internal.data.DefaultCell
import org.hexworks.zircon.internal.graphics.DefaultTileImage
import kotlin.math.max

abstract class BaseTileImage : TileImage {

    override fun fetchCells(): Iterable<Cell> {
        return fetchCellsBy(Position.defaultPosition(), size)
    }

    override fun fetchCellsBy(offset: Position, size: Size): Iterable<Cell> {
        return size.fetchPositions()
                .map { pos -> pos + offset }
                .map { pos -> DefaultCell(pos, getTileAt(pos).orElse(Tile.empty())) }
    }

    override fun withTileAt(position: Position, tile: Tile): TileImage {
        if (size.containsPosition(position)) {
            val tiles = toTileMap()
            val originalTile = getTileAt(position)
            if (originalTile.isPresent && originalTile.get() == tile) {
                return this
            }
            tiles[position] = tile
            return DefaultTileImage(
                    size = size,
                    tileset = currentTileset(),
                    tiles = tiles.toMap())
        } else {
            return this
        }
    }

    override fun withNewSize(newSize: Size): TileImage {
        return withNewSize(newSize, Tile.empty())
    }

    override fun withNewSize(newSize: Size, filler: Tile): TileImage {
        val oldTiles = toTileMap()
        val tiles = mutableMapOf<Position, Tile>()
        oldTiles.filterKeys { newSize.containsPosition(it) }
                .forEach { (pos, tile) ->
                    tiles[pos] = tile
                }
        if (filler != Tile.empty()) {
            newSize.fetchPositions().subtract(size.fetchPositions()).forEach {
                tiles[it] = filler
            }
        }
        return DefaultTileImage(
                size = newSize,
                tileset = currentTileset(),
                tiles = tiles.toMap())
    }

    override fun withFiller(filler: Tile): TileImage {
        if (filler == Tile.empty()) {
            return this
        }
        val tiles = toTileMap()
        size.fetchPositions().subtract(fetchFilledPositions()).forEach { pos ->
            tiles[pos] = filler
        }
        return DefaultTileImage(
                size = size,
                tileset = currentTileset(),
                tiles = tiles.toMap())
    }

    override fun withText(text: String, style: StyleSet, position: Position): TileImage {
        val tiles = toTileMap()
        text.forEachIndexed { col, char ->
            tiles[position.withRelativeX(col)] = TileBuilder
                    .newBuilder()
                    .withStyleSet(style)
                    .withCharacter(char)
                    .build()
        }
        return DefaultTileImage(
                size = size,
                tileset = currentTileset(),
                tiles = tiles.toMap())
    }

    override fun withStyle(styleSet: StyleSet,
                           offset: Position,
                           size: Size,
                           keepModifiers: Boolean): TileImage {
        val tiles = toTileMap()
        size.fetchPositions().forEach { pos ->
            pos.plus(offset).let { fixedPos ->
                getTileAt(fixedPos).map { tile: Tile ->
                    val oldMods = tile.styleSet.modifiers
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
                size = size,
                tileset = currentTileset(),
                tiles = tiles.toMap())
    }

    override fun withTileset(tileset: TilesetResource): TileImage {
        return DefaultTileImage(
                size = size,
                tileset = tileset,
                tiles = toTileMap().toMap())
    }

    override fun combineWith(tileImage: TileImage, offset: Position): TileImage {
        val columns = max(width, offset.x + tileImage.width)
        val rows = max(height, offset.y + tileImage.height)
        val newSize = Size.create(columns, rows)

        val tiles = toTileMap()
        tiles.putAll(tileImage.toTileMap().mapKeys { it.key + offset })
        return DefaultTileImage(
                size = newSize,
                tileset = currentTileset(),
                tiles = tiles.toMap())
    }

    override fun transform(transformer: TileTransformer): TileImage {
        val tiles = mutableMapOf<Position, Tile>()
        fetchCells().forEach { (pos, tile) ->
            tiles[pos] = transformer.invoke(tile)
        }
        return DefaultTileImage(
                size = size,
                tileset = currentTileset(),
                tiles = tiles.toMap())
    }

    override fun toSubImage(offset: Position, size: Size): TileImage {
        val tiles = mutableMapOf<Position, Tile>()
        size.fetchPositions()
                .map { it + offset }
                .intersect(this.size.fetchPositions())
                .forEach {
                    tiles[it - offset] = getTileAt(it).get()
                }
        return DefaultTileImage(
                size = size,
                tileset = currentTileset(),
                tiles = tiles.toMap())
    }

    override fun toTileMap(): MutableMap<Position, Tile> {
        val tileMap = mutableMapOf<Position, Tile>()
        fetchFilledPositions().forEach { pos ->
            getTileAt(pos).map { tile ->
                tileMap[pos] = tile
            }
        }
        return tileMap
    }

    override fun toTileImage(): TileImage = toSubImage(Position.defaultPosition(), size)

    override fun toTileGraphic(): TileGraphics {
        val result = TileGraphicsBuilder.newBuilder()
                .withSize(size)
                .withTileset(currentTileset())
        toTileMap().forEach { (pos, tile) ->
            result.withTile(pos, tile)
        }
        return result.build()
    }
}
