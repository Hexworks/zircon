package org.hexworks.zircon.api.game.base

import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.impl.Position3D
import org.hexworks.zircon.api.game.Cell3D

abstract class BaseCell3D<T : Tile, B : Block<T>> : Cell3D<T, B> {

    override fun withPosition(position: Position3D): Cell3D<T, B> {
        return if (position == this.position) {
            this
        } else {
            Cell3D.create(position, block)
        }
    }

    override fun withBlock(block: B): Cell3D<T, B> {
        return if (block == this.block) {
            this
        } else {
            Cell3D.create(position, block)
        }
    }
}
