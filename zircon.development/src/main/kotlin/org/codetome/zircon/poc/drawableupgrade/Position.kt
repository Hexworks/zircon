package org.codetome.zircon.poc.drawableupgrade

data class Position(val x: Int, val y: Int) : Comparable<Position> {

    operator fun plus(other: Position) = Position(x + other.x, y + other.y)

    override fun compareTo(other: Position): Int = when {
        y > other.y -> 1
        y == other.y && x > other.x -> 1
        y == other.y && x == other.x -> 0
        else -> -1
    }
}
