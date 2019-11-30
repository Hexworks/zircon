package org.hexworks.zircon.internal.util

import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.BlockTileType
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.game.GameArea
import org.hexworks.zircon.internal.game.GameAreaState

typealias AnyGameArea = GameArea<out Tile, out Block<out Tile>>

typealias AnyGameAreaState = GameAreaState<out Tile, out Block<out Tile>>

typealias RenderSequence = Sequence<Pair<Position3D, BlockTileType>>

fun AnyGameAreaState.isPositionVisible(position3D: Position3D): Boolean {
    return visibleSize.containsPosition(position3D - visibleOffset)
}