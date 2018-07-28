package org.codetome.zircon.poc.drawableupgrade.position

data class GridPosition(override val x: Int,
                        override val y: Int) : Position, Comparable<GridPosition> {

    operator fun plus(other: GridPosition) = GridPosition(x + other.x, y + other.y)

    override fun compareTo(other: GridPosition): Int = when {
        y > other.y -> 1
        y == other.y && x > other.x -> 1
        y == other.y && x == other.x -> 0
        else -> -1
    }

    override fun toAbsolutePosition(tileWidth: Int, tileHeight: Int): AbsolutePosition {
        return AbsolutePosition(x * tileWidth, y * tileHeight)
    }
}
