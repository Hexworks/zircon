package org.hexworks.zircon.internal.component.theme.neobrutalism

import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.component.data.ComponentState
import org.hexworks.zircon.api.behavior.Boundable
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderContext
import org.hexworks.zircon.internal.component.theme.neobrutalism.NeoFrameDecoration.Companion.fillEmptyCells
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.DrawWindow

/**
 * The link button's 4px bar: a `▂` row under the text. On hover the bar grows to the whole
 * button in HyperUI, so here the row fills with the (inverted) highlight background.
 */
public class NeoUnderlineDecoration(
    private val palette: NeobrutalismPaletteProvider,
) : ComponentDecorationRenderer {

    override val offset: Position = Position.ZERO
    override val occupiedSize: Size = Size(0, 1)

    override fun render(drawWindow: DrawWindow, context: ComponentDecorationRenderContext) {
        val style = context.currentStyle
        val colors = palette.paletteFor(context.component)
        val y = drawWindow.size.height - 1
        val hovered = context.component.componentState == ComponentState.HIGHLIGHTED ||
                context.component.componentState == ComponentState.ACTIVE
        val tile = if (hovered) {
            characterTile { character = ' '; styleSet = style }
        } else {
            characterTile {
                character = '▂'
                styleSet = style.withForegroundColor(colors?.ink ?: style.foregroundColor)
            }
        }
        drawWindow.fillEmptyCells(style, Boundable.create(Position.ZERO, Size(drawWindow.size.width, y)))
        for (x in 0 until drawWindow.size.width) drawWindow.draw(tile, Position(x, y))
    }
}
