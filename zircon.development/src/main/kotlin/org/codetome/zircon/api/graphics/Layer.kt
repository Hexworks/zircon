package org.codetome.zircon.api.graphics

import org.codetome.zircon.api.behavior.Movable
import org.codetome.zircon.api.data.Position

interface Layer<T : Any, S: Any> : TileImage<T, S>, Movable {

    fun position(): Position
}
