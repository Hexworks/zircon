package org.hexworks.zircon.internal.graphics

import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.persistentHashMapOf
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.TileImage
import org.hexworks.zircon.api.resource.TilesetResource

/**
 * Since [org.hexworks.zircon.api.graphics.TileImage]s are immutable, we can use a simple
 * [PersistentMap] to store the tiles that also allows for efficient copying.
 */
class DefaultTileImage internal constructor(
    override val size: Size,
    override val tileset: TilesetResource,
    initialTiles: Map<Position, Tile>
) : TileImage {

    override val tiles: PersistentMap<Position, Tile> = persistentHashMapOf<Position, Tile>()
        .putAll(initialTiles)

}
