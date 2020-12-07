package org.hexworks.zircon.internal.graphics

import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.persistentHashMapOf
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.base.BaseTileImage
import org.hexworks.zircon.api.resource.TilesetResource

class DefaultTileImage(
    override val size: Size,
    override val tileset: TilesetResource,
    initialTiles: Map<Position, Tile> = mapOf()
) : BaseTileImage() {

    override val tiles: PersistentMap<Position, Tile> = persistentHashMapOf<Position, Tile>()
        .putAll(initialTiles)

}
