package org.codetome.zircon.poc.drawableupgrade.drawables

import org.codetome.zircon.poc.drawableupgrade.position.GridPosition

/**
 * Represents an object which can be drawn onto a [DrawSurface].
 */
interface Drawable<T: Any> {

    /**
     * Draws this [Drawable] onto the given [DrawSurface] at the given `offset` position.
     */
    fun drawOnto(surface: DrawSurface<T>, offset: GridPosition)
}
