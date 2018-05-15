package org.codetome.zircon.api

expect object PositionFactory {

    fun create(x: Int, y: Int): Position
}
