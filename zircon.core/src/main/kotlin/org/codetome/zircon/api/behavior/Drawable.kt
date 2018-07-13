package org.codetome.zircon.api.behavior

import org.codetome.zircon.api.Position

/**
 * Represents an object which can be drawn upon a [DrawSurface].
 */
interface Drawable : Boundable {

    /**
     * Copies this [Drawable]'s content to a [DrawSurface]. If the destination [DrawSurface] is larger
     * than this [Drawable], the areas outside of the area that is written to will be untouched.
     */
    fun drawOnto(surface: DrawSurface, offset: Position = Position.topLeftCorner())

}
