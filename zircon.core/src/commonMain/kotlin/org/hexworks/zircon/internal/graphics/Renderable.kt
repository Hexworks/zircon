package org.hexworks.zircon.internal.graphics

import org.hexworks.zircon.api.behavior.Boundable
import org.hexworks.zircon.api.graphics.TileGraphics

/**
 * Represents an object that can be rendered on the screen at a specific ([Renderable.position]).
 */
interface Renderable : Boundable {

    /**
     * Renders this [Renderable] onto the given [TileGraphics] object.
     */
    fun render(graphics: TileGraphics)
}
