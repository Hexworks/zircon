package org.codetome.zircon.poc.drawableupgrade.tileimage

import org.codetome.zircon.poc.drawableupgrade.position.GridPosition
import org.codetome.zircon.poc.drawableupgrade.drawables.Layer
import org.codetome.zircon.poc.drawableupgrade.drawables.TileImage

/**
 * this is a basic building block which can be re-used by complex image
 * classes like layers, boxes, components, and more
 * all classes which are implementing the DrawSurface or the Drawable operations can
 * use this class as a base class just like how the TileGrid uses it
 */

data class DefaultLayer<T : Any, S : Any>(val position: GridPosition,
                                          val backend: TileImage<T, S>)
    : Layer<T, S>, TileImage<T, S> by backend {

    override fun position() = position

}
