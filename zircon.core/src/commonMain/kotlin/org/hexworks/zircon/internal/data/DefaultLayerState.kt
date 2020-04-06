package org.hexworks.zircon.internal.data

import kotlinx.collections.immutable.PersistentMap
import org.hexworks.cobalt.core.api.UUID
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.resource.TilesetResource

data class DefaultLayerState(
        override val tiles: PersistentMap<Position, Tile>,
        override val tileset: TilesetResource,
        override val size: Size,
        override val id: UUID,
        override val position: Position,
        override val isHidden: Boolean
) : LayerState
