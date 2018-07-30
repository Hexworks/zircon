package org.codetome.zircon.api.graphics

import org.codetome.zircon.api.data.Position

interface Layer<T : Any, S: Any> : TileImage<T, S> {

    fun position(): Position
}
