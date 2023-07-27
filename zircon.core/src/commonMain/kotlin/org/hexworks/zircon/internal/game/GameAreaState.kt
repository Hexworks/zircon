package org.hexworks.zircon.internal.game

import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Size3D
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.game.GameAreaTileFilter

data class GameAreaState<T : Tile, B : Block<T>>(
    val blocks: MutableMap<Position3D, B>,
    val actualSize: Size3D,
    val visibleSize: Size3D,
    val visibleOffset: Position3D,
    val filter: GameAreaTileFilter<T>
)
