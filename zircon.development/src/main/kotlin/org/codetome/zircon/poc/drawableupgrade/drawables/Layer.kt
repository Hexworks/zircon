package org.codetome.zircon.poc.drawableupgrade.drawables

import org.codetome.zircon.poc.drawableupgrade.position.GridPosition

interface Layer<T : Any, S: Any> : TileImage<T, S> {

    fun position(): GridPosition
}
