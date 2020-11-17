package org.hexworks.zircon.internal.game.impl

import kotlinx.collections.immutable.PersistentMap
import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Size3D
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.game.GameAreaTileFilter
import org.hexworks.zircon.api.game.base.BaseGameArea

class DefaultGameArea<T : Tile, B : Block<T>>(
        initialVisibleSize: Size3D,
        initialActualSize: Size3D,
        initialVisibleOffset: Position3D,
        initialContents: PersistentMap<Position3D, B>,
        initialFilters: Iterable<GameAreaTileFilter>
) : BaseGameArea<T, B>(
        initialVisibleSize = initialVisibleSize,
        initialActualSize = initialActualSize,
        initialVisibleOffset = initialVisibleOffset,
        initialContents = initialContents,
        initialFilters = initialFilters
)
