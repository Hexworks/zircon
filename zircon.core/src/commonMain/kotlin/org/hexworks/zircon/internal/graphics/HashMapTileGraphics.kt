package org.hexworks.zircon.internal.graphics

import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.base.BaseTileGraphics
import org.hexworks.zircon.api.resource.TilesetResource

class HashMapTileGraphics(
    initialSize: Size,
    initialTileset: TilesetResource,
    initialTiles: Map<Position, Tile> = mapOf()
) : BaseTileGraphics(
    initialSize = initialSize,
    initialTileset = initialTileset
) {

    override val tiles = initialTiles.toMutableMap()

    override fun draw(tile: Tile, drawPosition: Position) {
        if (size.containsPosition(drawPosition)) {
            tiles[drawPosition] = tile
        }
    }

    override fun draw(tileMap: Map<Position, Tile>, drawPosition: Position, drawArea: Size) {
        tileMap.forEach { (pos, tile) ->
            val finalPos = drawPosition + pos
            if (drawArea.containsPosition(pos) && size.containsPosition(finalPos)) {
                tiles[finalPos] = tile
            }
        }
    }

    override fun transform(transformer: (Position, Tile) -> Tile) {
        tiles.forEach { (pos, tile) ->
            tiles[pos] = transformer(pos, tile)
        }
    }

    override fun fill(filler: Tile) {
        size.fetchPositions().forEach { pos ->
            val tile = tiles[pos]
            if (tile == null || tile.isEmpty) {
                tiles[pos] = filler
            }
        }
    }

    override fun clear() {
        tiles.clear()
    }
}