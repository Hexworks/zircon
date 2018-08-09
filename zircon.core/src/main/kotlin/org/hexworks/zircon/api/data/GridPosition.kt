package org.hexworks.zircon.api.data

import org.hexworks.zircon.api.tileset.Tileset

data class GridPosition(override val x: Int,
                        override val y: Int) : Position {

    override fun toAbsolutePosition(tileset: Tileset<out Any>): AbsolutePosition {
        return AbsolutePosition(x * tileset.width(), y * tileset.height())
    }

    companion object {

        fun create(x: Int, y: Int): Position = GridPosition(x, y)
    }
}
