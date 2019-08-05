package org.hexworks.zircon.internal.data

import org.hexworks.zircon.api.data.LayerSnapshot
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.resource.TilesetResource

class DefaultLayerSnapshot(override val tiles: Map<Position, Tile>,
                           override val tileset: TilesetResource,
                           override val size: Size,
                           override val position: Position) : LayerSnapshot
