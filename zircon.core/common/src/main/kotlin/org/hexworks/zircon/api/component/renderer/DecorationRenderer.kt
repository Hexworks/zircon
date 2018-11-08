package org.hexworks.zircon.api.component.renderer

import org.hexworks.zircon.api.graphics.impl.SubTileGraphics
import org.hexworks.zircon.api.graphics.TileGraphics

/**
 * A [DecorationRenderer] is responsible for rendering decorations
 * onto [TileGraphics] objects.
 * See [here](https://docs.google.com/drawings/d/1-gkoXeKblh8qOcd5XHGP1z5qP8lN8eBPvfVvWx8r5Tk/edit?usp=sharing)
 * for more details.
 */
interface DecorationRenderer<T: RenderContext> {

    /**
     * Renders the decoration to the given [SubTileGraphics]. Note that the supplied [SubTileGraphics]
     * is already constrained to the bounds of this renderer so no extra positioning is
     * necessary.
     */
    fun render(tileGraphics: SubTileGraphics, context: T)
}
