package org.codetome.zircon.api.behavior

import org.codetome.zircon.api.data.Position

/**
 * Represents an object which can be drawn upon a [DrawSurface].
 */
interface Drawable {

    /**
     * Copies this [Drawable]'s content to a [DrawSurface]. If the destination [DrawSurface]
     * is larger than this [Drawable], the areas outside of the area that is written to
     * will be untouched.
     * Conversely if the [Drawable] is bigger than the target [DrawSurface] the overflowing
     * parts won't be written. Drawing happens from left to right then from top to bottom.
     */
    fun drawOnto(surface: DrawSurface, offset: Position = Position.topLeftCorner())

}
