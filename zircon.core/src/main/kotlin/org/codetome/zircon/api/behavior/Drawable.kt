package org.codetome.zircon.api.behavior

import org.codetome.zircon.api.Position

/**
 * Represents an object which can be drawn upon a [DrawSurface].
 */
interface Drawable : Boundable {

    /**
     * Copies this [Drawable]'s content to a [DrawSurface]. If the destination [DrawSurface] is larger
     * than this [Drawable], the areas outside of the area that is written to will be untouched.
     * Conversely if the [Drawable] is bigger than the target [DrawSurface] the overflowing parts won't
     * be written. Drawing happens from left to right then from top to bottom.
     *
     * @param surface the surface to draw to
     * @param offset the position on the surface where drawing starts
     */
    fun drawOnto(surface: DrawSurface, offset: Position = Position.topLeftCorner())

}
