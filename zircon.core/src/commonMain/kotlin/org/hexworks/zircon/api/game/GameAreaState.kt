package org.hexworks.zircon.api.game

import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.impl.Position3D
import org.hexworks.zircon.api.data.impl.Size3D

data class GameAreaState<T : Tile, B : Block<T>>(
        val blocks: Map<Position3D, B>,
        val actualSize: Size3D,
        val visibleSize: Size3D,
        val visibleOffset: Position3D)