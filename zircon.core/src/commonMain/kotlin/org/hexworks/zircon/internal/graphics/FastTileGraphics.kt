package org.hexworks.zircon.internal.graphics

import org.hexworks.zircon.api.Tiles
import org.hexworks.zircon.api.data.DrawSurfaceState
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.api.graphics.base.BaseTileGraphics
import org.hexworks.zircon.api.resource.TilesetResource

/**
 * This is a non thread-safe, but very fast [TileGraphics]
 * implementation.
 */
class FastTileGraphics(
        initialSize: Size,
        initialTileset: TilesetResource,
        initialTiles: Map<Position, Tile>)
    : BaseTileGraphics(
        initialTileset = initialTileset,
        initialSize = initialSize) {

    override val tiles = initialTiles.toMutableMap()

    override val state: DrawSurfaceState
        get() = DrawSurfaceState.create(
                tiles = tiles.toMap(),
                tileset = tileset,
                size = size)

    override fun draw(tileMap: Map<Position, Tile>, drawPosition: Position, drawArea: Size) {
        this.tiles.putAll(tileMap.asSequence()
                .filter { drawArea.containsPosition(it.key) && size.containsPosition(it.key + drawPosition) }
                .map { it.key + drawPosition to it.value }
                .toMap())
    }

    override fun draw(tile: Tile, drawPosition: Position) {
        if (size.containsPosition(drawPosition)) {
            tiles[drawPosition] = tile
        }
    }

    override fun fill(filler: Tile) {
        size.fetchPositions().minus(tiles.keys.filter { it != Tiles.empty() }).forEach {
            tiles[it] = filler
        }
    }

    override fun clear() {
        tiles.clear()
    }

    override fun transformTileAt(position: Position, tileTransformer: (Tile) -> Tile) {
        getTileAt(position).map { tile ->
            tiles[position] = tileTransformer(tile)
        }
    }
}
