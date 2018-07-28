package org.codetome.zircon.poc.drawableupgrade.position

data class AbsolutePosition(override val x: Int,
                            override val y: Int) : Position, Comparable<AbsolutePosition> {

    operator fun plus(other: AbsolutePosition) = AbsolutePosition(x + other.x, y + other.y)

    override fun compareTo(other: AbsolutePosition): Int = when {
        y > other.y -> 1
        y == other.y && x > other.x -> 1
        y == other.y && x == other.x -> 0
        else -> -1
    }

    override fun toAbsolutePosition(tileWidth: Int, tileHeight: Int) = this
}
