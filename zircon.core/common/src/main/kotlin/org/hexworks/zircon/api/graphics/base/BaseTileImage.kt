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
import org.hexworks.zircon.platform.factory.PersistentMapFactory
import kotlin.math.max

abstract class BaseTileImage : TileImage {

    override fun withTileAt(position: Position, tile: Tile): TileImage {
        return if (size.containsPosition(position)) {
            DefaultTileImage(
                    size = size,
                    tileset = tileset,
                    initialTiles = tiles.put(position, tile))
        } else {
            this
        }
    }

    override fun withNewSize(newSize: Size): TileImage {
        return withNewSize(newSize, Tile.empty())
    }

    override fun withNewSize(newSize: Size, filler: Tile): TileImage {
        if (newSize == size) return this
        var newTiles = PersistentMapFactory.create<Position, Tile>()
        tiles.filterKeys { newSize.containsPosition(it) }
                .forEach { (pos, tile) ->
                    newTiles = newTiles.put(pos, tile)
                }
        if (filler != Tile.empty()) {
            newSize.fetchPositions().subtract(size.fetchPositions()).forEach {
                newTiles = newTiles.put(it, filler)
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
        var newTiles = tiles
        size.fetchPositions().subtract(tiles.keys).forEach { pos ->
            newTiles = newTiles.put(pos, filler)
        }
        return DefaultTileImage(
                size = size,
                tileset = tileset,
                initialTiles = newTiles)
    }

    override fun withText(text: String, style: StyleSet, position: Position): TileImage {
        var newTiles = tiles
        text.forEachIndexed { col, char ->
            newTiles = newTiles.put(position.withRelativeX(col), TileBuilder
                    .newBuilder()
                    .withStyleSet(style)
                    .withCharacter(char)
                    .build())
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
        var newTiles = tiles
        size.fetchPositions().forEach { pos ->
            pos.plus(offset).let { fixedPos ->
                getTileAt(fixedPos).map { tile: Tile ->
                    val oldMods = tile.styleSet.modifiers
                    val newTile = if (keepModifiers) {
                        tile.withStyle(styleSet.withAddedModifiers(oldMods))
                    } else {
                        tile.withStyle(styleSet)
                    }
                    newTiles = newTiles.put(fixedPos, newTile)
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
                initialTiles = tiles)
    }

    override fun combineWith(tileImage: TileImage, offset: Position): TileImage {
        val columns = max(width, offset.x + tileImage.width)
        val rows = max(height, offset.y + tileImage.height)
        val newSize = Size.create(columns, rows)

        var newTiles = tiles
        newTiles = newTiles.putAll(tileImage.tiles.mapKeys { it.key + offset })
        return DefaultTileImage(
                size = newSize,
                tileset = tileset,
                initialTiles = newTiles)
    }

    override fun transform(transformer: (Tile) -> Tile): TileImage {
        var newTiles = tiles
        tiles.forEach { (pos, tile) ->
            newTiles = newTiles.put(pos, transformer.invoke(tile))
        }
        return DefaultTileImage(
                size = size,
                tileset = tileset,
                initialTiles = newTiles)
    }

    override fun toSubImage(offset: Position, size: Size): TileImage {
        var newTiles = PersistentMapFactory.create<Position, Tile>()
        size.fetchPositions()
                .map { it + offset }
                .intersect(this.size.fetchPositions())
                .forEach { pos ->
                    getTileAt(pos).map { tile ->
                        newTiles = newTiles.put(pos - offset, tile)
                    }
                }
        return DefaultTileImage(
                size = size,
                tileset = tileset,
                initialTiles = newTiles)
    }

    override fun toTileImage() = this

    override fun toTileGraphics() = TileGraphicsBuilder.newBuilder()
            .withSize(size)
            .withTileset(tileset)
            .withTiles(tiles)
            .build()
}
