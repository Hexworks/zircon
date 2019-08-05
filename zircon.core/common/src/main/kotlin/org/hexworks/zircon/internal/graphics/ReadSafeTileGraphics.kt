package org.hexworks.zircon.internal.graphics

import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.DrawSurface
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.api.graphics.base.BaseTileGraphics
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.platform.factory.PersistentMapFactory

/**
 * This is a read-safe [TileGraphics] implementation. Read safety means
 * that all read operations ([getTileAt], [createSnapshot], etc) are consistent
 * even if concurrent write operations are being performed. Use this implementation
 * if you own't use multiple threads when writing to this object.
 */
class ReadSafeTileGraphics(
        size: Size,
        tileset: TilesetResource,
        initialTiles: Map<Position, Tile> = mapOf())
    : BaseTileGraphics(
        tileset = tileset,
        initialSize = size) {

    override var tiles = PersistentMapFactory.create<Position, Tile>()
            .putAll(initialTiles)
        private set

    override fun drawOnto(surface: DrawSurface, position: Position) {
        tiles = tiles.putAll(surface.tiles.mapKeys { it.key + position })
    }

    override fun clear() {
        tiles = tiles.clear()
    }

    override fun setTileAt(position: Position, tile: Tile) {
        if (size.containsPosition(position)) {
            tiles = tiles.put(position, tile)
        }
    }

    override fun transformTileAt(position: Position, tileTransformer: (Tile) -> Tile) {
        tiles[position]?.let { tile ->
            tiles = tiles.put(position, tileTransformer(tile))
        }
    }
}
