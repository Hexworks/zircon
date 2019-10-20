package org.hexworks.zircon.api.game.base

import org.hexworks.zircon.api.behavior.Scrollable3D
import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.impl.Position3D
import org.hexworks.zircon.api.data.impl.Size3D
import org.hexworks.zircon.api.game.GameArea
import org.hexworks.zircon.api.game.GameArea.Companion.fetchPositionsWithOffset
import org.hexworks.zircon.internal.behavior.impl.DefaultScrollable3D

abstract class BaseGameArea<T : Tile, B : Block<T>>(initialVisibleSize: Size3D,
                                                    initialActualSize: Size3D)
    : GameArea<T, B>, Scrollable3D by DefaultScrollable3D(
        initialVisibleSize = initialVisibleSize,
        initialActualSize = initialActualSize) {

    override fun fetchBlocksAt(offset: Position3D, size: Size3D): Map<Position3D, B> {
        return fetchPositionsWithOffset(offset, size)
                .filter { hasBlockAt(it) }
                .map { it to fetchBlockOrDefault(it) }
                .toMap()
    }

    override fun fetchBlocksAtLevel(z: Int): Map<Position3D, B> {
        return fetchBlocksAt(
                offset = Position3D.create(0, 0, z),
                size = actualSize)
    }

}
