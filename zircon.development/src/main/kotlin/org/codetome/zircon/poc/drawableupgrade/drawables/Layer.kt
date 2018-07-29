package org.codetome.zircon.poc.drawableupgrade.drawables

import org.codetome.zircon.api.data.Position

interface Layer<T : Any, S: Any> : TileImage<T, S> {

    fun position(): Position
}
