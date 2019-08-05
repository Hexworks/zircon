package org.hexworks.zircon.internal.graphics

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.DrawSurface
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.api.graphics.base.BaseTileGraphics
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.platform.factory.PersistentMapFactory
import org.hexworks.zircon.platform.util.Dispatchers
import kotlin.coroutines.CoroutineContext

/**
 * This is a thread-safe [TileGraphics] implementation. It offers consistent
 * snapshot creation, and consistent writes.
 * **Note that** writes are confined to one thread so batch writing might be
 * inefficient (eg: calling [setTileAt] repeatedly). Consider using [FastTileGraphics]
 * or [ReadSafeTileGraphics] if you need to do many transformations,
 * but you only use one thread.
 */
class ThreadSafeTileGraphics(
        size: Size,
        tileset: TilesetResource,
        initialTiles: Map<Position, Tile> = mapOf())
    : BaseTileGraphics(
        tileset = tileset,
        initialSize = size), CoroutineScope {

    override val tiles: Map<Position, Tile>
        get() = contents
    override val coroutineContext: CoroutineContext = Dispatchers.Single

    private var contents = PersistentMapFactory.create<Position, Tile>()
            .putAll(initialTiles)

    override fun drawOnto(surface: DrawSurface, position: Position) {
        launch {
            contents = contents.putAll(surface.tiles.mapKeys { it.key + position })
        }
    }

    override fun clear() {
        launch {
            contents = contents.clear()
        }
    }

    override fun setTileAt(position: Position, tile: Tile) {
        launch {
            if (size.containsPosition(position)) {
                contents[position]?.let { previous ->
                    if (previous != tile) {
                        contents = contents.put(position, tile)
                    }
                } ?: run {
                    contents = contents.put(position, tile)
                }
            }
        }
    }

    override fun transformTileAt(position: Position, tileTransformer: (Tile) -> Tile) {
        launch {
            getTileAt(position).map { tile ->
                setTileAt(position, tileTransformer(tile))
            }
        }
    }
}
