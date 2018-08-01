package org.codetome.zircon.api.behavior

import org.codetome.zircon.api.data.Position

/**
 * Represents an object which can be drawn onto a [DrawSurface].
 */
interface Drawable<T: Any> {

    /**
     * Draws this [Drawable] onto the given [DrawSurface] at the given `offset` position.
     */
    fun drawOnto(surface: DrawSurface<T>, offset: Position = Position.topLeftCorner())
}
