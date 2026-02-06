package org.hexworks.zircon.internal.graphics

import org.hexworks.zircon.api.behavior.Boundable
import org.hexworks.zircon.api.behavior.CanBeHidden
import org.hexworks.zircon.api.behavior.TilesetOverride
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.api.graphics.DrawSurface
/**
 * Represents an object that can be rendered on the screen at a specific ([Renderable.position]).
 * Differs from a [TileGraphics] in that it doesn't expose its internals (its tiles) and it can't be
 * drawn upon (doesn't implement [DrawSurface]). Can be converted into one using [render].
 */
interface Renderable : Boundable, CanBeHidden, TilesetOverride {

    /**
     * Renders this [Renderable] to a [TileGraphics].
     */
    fun render(): TileGraphics

    /**
     * Renders this [Renderable] on top of the given [graphics].
     */
    fun render(graphics: TileGraphics)

}
