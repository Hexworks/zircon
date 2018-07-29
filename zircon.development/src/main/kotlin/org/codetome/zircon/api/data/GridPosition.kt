package org.codetome.zircon.api.data

import org.codetome.zircon.poc.drawableupgrade.tileset.Tileset

data class GridPosition(override val x: Int,
                        override val y: Int) : Position {

    operator fun plus(other: GridPosition) = GridPosition(x + other.x, y + other.y)

    override fun compareTo(other: Position): Int {
        require(other is GridPosition)
        return when {
            y > other.y -> 1
            y == other.y && x > other.x -> 1
            y == other.y && x == other.x -> 0
            else -> -1
        }
    }

    override fun toAbsolutePosition(tileset: Tileset<out Any, out Any>): AbsolutePosition {
        return AbsolutePosition(x * tileset.width(), y * tileset.height())
    }

    companion object {

        fun create(x: Int, y: Int): Position = GridPosition(x, y)
    }
}
