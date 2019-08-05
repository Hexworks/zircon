package org.hexworks.zircon.api.graphics.base

import org.hexworks.zircon.api.builder.data.TileBuilder
import org.hexworks.zircon.api.builder.graphics.TileGraphicsBuilder
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.graphics.TileImage
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.graphics.DefaultTileImage
import kotlin.math.max

abstract class BaseTileImage : TileImage {

    override fun withTileAt(position: Position, tile: Tile): TileImage {
        if (size.containsPosition(position)) {
            val originalTile = getTileAt(position)
            if (originalTile.isPresent && originalTile.get() == tile) {
                return this
            }
            val newTiles = toTileMap()
            newTiles[position] = tile
            return DefaultTileImage(
                    size = size,
                    tileset = tileset,
                    initialTiles = newTiles.toMap())
        } else {
            return this
        }
    }

    override fun withNewSize(newSize: Size): TileImage {
        return withNewSize(newSize, Tile.empty())
    }

    override fun withNewSize(newSize: Size, filler: Tile): TileImage {
        val oldTiles = toTileMap()
        val newTiles = mutableMapOf<Position, Tile>()
        oldTiles.filterKeys { newSize.containsPosition(it) }
                .forEach { (pos, tile) ->
                    newTiles[pos] = tile
                }
        if (filler != Tile.empty()) {
            newSize.fetchPositions().subtract(size.fetchPositions()).forEach {
                newTiles[it] = filler
            }
        }
        return DefaultTileImage(
                size = newSize,
                tileset = tileset,
                initialTiles = newTiles)
    }

    override fun withFiller(filler: Tile): TileImage {
        if (filler == Tile.empty()) {
            return this
        }
        val newTiles = toTileMap()
        size.fetchPositions().subtract(tiles.keys).forEach { pos ->
            newTiles[pos] = filler
        }
        return DefaultTileImage(
                size = size,
                tileset = tileset,
                initialTiles = tiles)
    }

    override fun withText(text: String, style: StyleSet, position: Position): TileImage {
        val newTiles = toTileMap()
        text.forEachIndexed { col, char ->
            newTiles[position.withRelativeX(col)] = TileBuilder
                    .newBuilder()
                    .withStyleSet(style)
                    .withCharacter(char)
                    .build()
        }
        return DefaultTileImage(
                size = size,
                tileset = tileset,
                initialTiles = newTiles)
    }

    override fun withStyle(styleSet: StyleSet,
                           offset: Position,
                           size: Size,
                           keepModifiers: Boolean): TileImage {
        val newTiles = toTileMap()
        size.fetchPositions().forEach { pos ->
            pos.plus(offset).let { fixedPos ->
                getTileAt(fixedPos).map { tile: Tile ->
                    val oldMods = tile.styleSet.modifiers
                    val newTile = if (keepModifiers) {
                        tile.withStyle(styleSet.withAddedModifiers(oldMods))
                    } else {
                        tile.withStyle(styleSet)
                    }
                    newTiles[fixedPos] = newTile
                }
            }
        }
        return DefaultTileImage(
                size = size,
                tileset = tileset,
                initialTiles = newTiles)
    }

    override fun withTileset(tileset: TilesetResource): TileImage {
        return DefaultTileImage(
                size = size,
                tileset = tileset,
                initialTiles = toTileMap())
    }

    override fun combineWith(tileImage: TileImage, offset: Position): TileImage {
        val columns = max(width, offset.x + tileImage.width)
        val rows = max(height, offset.y + tileImage.height)
        val newSize = Size.create(columns, rows)

        val newTiles = toTileMap()
        newTiles.putAll(tileImage.toTileMap().mapKeys { it.key + offset })
        return DefaultTileImage(
                size = newSize,
                tileset = tileset,
                initialTiles = newTiles)
    }

    override fun transform(transformer: (Tile) -> Tile): TileImage {
        val newTiles = mutableMapOf<Position, Tile>()
        tiles.forEach { (pos, tile) ->
            newTiles[pos] = transformer.invoke(tile)
        }
        return DefaultTileImage(
                size = size,
                tileset = tileset,
                initialTiles = newTiles)
    }

    override fun toSubImage(offset: Position, size: Size): TileImage {
        val newTiles = mutableMapOf<Position, Tile>()
        size.fetchPositions()
                .map { it + offset }
                .intersect(this.size.fetchPositions())
                .forEach {
                    newTiles[it - offset] = getTileAt(it).get()
                }
        return DefaultTileImage(
                size = size,
                tileset = tileset,
                initialTiles = newTiles)
    }

    override fun toTileMap(): MutableMap<Position, Tile> {
        return tiles.toMutableMap()
    }

    override fun toTileImage(): TileImage = toSubImage(Position.defaultPosition(), size)

    override fun toTileGraphics() = TileGraphicsBuilder.newBuilder()
            .withSize(size)
            .withTileset(tileset)
            .withTiles(tiles)
            .build()
}
