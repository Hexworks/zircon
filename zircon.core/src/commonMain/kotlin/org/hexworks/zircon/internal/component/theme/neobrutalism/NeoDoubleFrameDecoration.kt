package org.hexworks.zircon.internal.component.theme.neobrutalism

import org.hexworks.zircon.api.behavior.Boundable
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderContext
import org.hexworks.zircon.internal.component.theme.neobrutalism.NeoFrameDecoration.Companion.fillEmptyCells
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.component.theme.border
import org.hexworks.zircon.api.component.theme.primary
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.graphics.DrawWindow
import org.hexworks.zircon.api.graphics.extensions.drawBox

/**
 * HyperUI's `ring-2 ring-offset-2`: a double line drawn on accent cells gives ink, accent,
 * ink. No shadow. The primary button.
 */
public class NeoDoubleFrameDecoration : ComponentDecorationRenderer {

    override val offset: Position = Position.OFFSET_1X1
    override val occupiedSize: Size = Size(2, 2)

    override fun render(drawWindow: DrawWindow, context: ComponentDecorationRenderContext) {
        val palette = context.component.componentPalette
        val style = context.currentStyle
        val borderStyle = style.withForegroundColor(palette.border).withBackgroundColor(palette.primary)
        drawWindow.fillEmptyCells(style, Boundable.create(Position.OFFSET_1X1, drawWindow.size - Size(2, 2)))
        drawWindow.drawBox(boxType = BoxType.DOUBLE, style = borderStyle)
    }
}
