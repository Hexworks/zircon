package org.hexworks.zircon.internal.game.impl

import kotlinx.collections.immutable.PersistentMap
import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Size3D
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.game.ProjectionMode
import org.hexworks.zircon.api.game.base.BaseGameArea
import org.hexworks.zircon.api.resource.TilesetResource

class DefaultGameArea<T : Tile, B : Block<T>>(
        initialVisibleSize: Size3D,
        initialActualSize: Size3D,
        initialVisibleOffset: Position3D,
        initialTileset: TilesetResource,
        initialContents: PersistentMap<Position3D, B>,
) : BaseGameArea<T, B>(
        initialVisibleSize = initialVisibleSize,
        initialActualSize = initialActualSize,
        initialVisibleOffset = initialVisibleOffset,
        initialContents = initialContents,
        initialTileset = initialTileset,
)
