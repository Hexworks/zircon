package org.hexworks.zircon.api.component.renderer

import org.hexworks.zircon.api.graphics.impl.DrawWindow

/**
 * A [DecorationRenderer] is responsible for rendering decorations onto [DrawWindow] objects.
 * A [DrawWindow] is an editing "window" over a graphics object. A [DecorationRenderer] is
 * typically used to add visual effects or decorations to components.
 */
fun interface DecorationRenderer<T : RenderContext> {

    /**
     * Renders the decoration to the given [DrawWindow].
     */
    fun render(drawWindow: DrawWindow, context: T)
}
