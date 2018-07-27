package org.codetome.zircon.poc.drawableupgrade.drawables

import org.codetome.zircon.poc.drawableupgrade.Position

/**
 * Represents an object which can be drawn onto a [DrawSurface].
 */
interface Drawable {

    /**
     * Draws this [Drawable] onto the given [DrawSurface] at the given `offset` position.
     */
    fun drawOnto(surface: DrawSurface, offset: Position)
}
