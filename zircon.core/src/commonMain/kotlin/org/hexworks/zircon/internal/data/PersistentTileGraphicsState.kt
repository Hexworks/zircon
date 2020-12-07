package org.hexworks.zircon.internal.data

import kotlinx.collections.immutable.PersistentMap
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.resource.TilesetResource

data class PersistentTileGraphicsState(
    override val tiles: PersistentMap<Position, Tile>,
    override val tileset: TilesetResource,
    override val size: Size
) : TileGraphicsState
