package org.hexworks.zircon.api.component.renderer

import org.hexworks.zircon.api.graphics.TileGraphics

/**
 * A [DecorationRenderer] is responsible for rendering decorations
 * onto [TileGraphics] objects. See
 * [here](https://docs.google.com/drawings/d/1-gkoXeKblh8qOcd5XHGP1z5qP8lN8eBPvfVvWx8r5Tk/edit?usp=sharing)
 * for more details.
 */
fun interface DecorationRenderer<T : RenderContext> {

    /**
     * Renders the decoration to the given [TileGraphics].
     */
    fun render(tileGraphics: TileGraphics, context: T)
}
