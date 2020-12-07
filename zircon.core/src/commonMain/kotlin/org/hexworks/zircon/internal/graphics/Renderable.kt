package org.hexworks.zircon.internal.graphics

import org.hexworks.zircon.api.behavior.Boundable
import org.hexworks.zircon.api.behavior.CanBeHidden
import org.hexworks.zircon.api.behavior.TilesetOverride
import org.hexworks.zircon.api.graphics.TileGraphics

/**
 * Represents an object that can be rendered on the screen at a specific ([Renderable.position]).
 */
interface Renderable : Boundable, CanBeHidden, TilesetOverride {

    /**
     * Renders this [Renderable] onto the given [TileGraphics] object.
     */
    fun render(graphics: TileGraphics)
}
