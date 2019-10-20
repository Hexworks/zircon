package org.hexworks.zircon.internal.graphics

import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.persistentMapOf
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.base.BaseTileImage
import org.hexworks.zircon.api.resource.TilesetResource

class DefaultTileImage(
        override val size: Size,
        override val tileset: TilesetResource,
        initialTiles: Map<Position, Tile> = mapOf())
    : BaseTileImage() {

    override val tiles: PersistentMap<Position, Tile> = persistentMapOf<Position, Tile>()
            .putAll(initialTiles)

}
