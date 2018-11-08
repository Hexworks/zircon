package org.hexworks.zircon.api.behavior

import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.graphics.DrawSurface

/**
 * Represents an object which can be drawn onto a [DrawSurface].
 */
interface Drawable {

    /**
     * Draws this [Drawable] onto the given [DrawSurface] at the given position.
     */
    fun drawOnto(surface: DrawSurface) {
        drawOnto(surface, Position.defaultPosition())
    }

    /**
     * Draws this [Drawable] onto the given [DrawSurface] at the given position.
     */
    fun drawOnto(surface: DrawSurface, position: Position)
}
