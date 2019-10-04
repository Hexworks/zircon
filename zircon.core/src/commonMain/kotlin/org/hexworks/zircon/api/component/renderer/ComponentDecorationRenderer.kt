package org.hexworks.zircon.api.component.renderer

import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.TileGraphics

/**
 * A [ComponentDecorationRenderer] is responsible for rendering decorations for a given
 * component.
 */
interface ComponentDecorationRenderer : DecorationRenderer<ComponentDecorationRenderContext> {

    /**
     * The [Position] this renderer offsets the [TileGraphics]. For example in the case of
     * a Box renderer which draws a box around the graphics the offset will be Position(1, 1)
     * because a box takes up 1 column and 1 row on the top and on the left.
     */
    val offset: Position

    /**
     * The [Size] this [ComponentDecorationRenderer] is occupying. For example a Box renderer
     * has an `occupiedSize` of `Size(2, 2)` since it takes up 2 columns (left, right)
     * and 2 rows (top, down).
     */
    val occupiedSize: Size

}
