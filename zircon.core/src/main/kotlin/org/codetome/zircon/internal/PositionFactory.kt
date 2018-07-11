package org.codetome.zircon.internal

import org.codetome.zircon.api.Position

expect object PositionFactory {

    fun create(x: Int, y: Int): Position
}
