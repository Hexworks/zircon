package org.hexworks.zircon.internal.component.renderer

import org.hexworks.zircon.api.behavior.Selectable
import org.hexworks.zircon.api.behavior.TextOverride
import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.builder.graphics.characterTileString
import org.hexworks.zircon.api.builder.graphics.styleSet
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.graphics.DrawWindow
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.component.theme.onSelection
import org.hexworks.zircon.api.component.theme.selection

/**
 * The stock tab of the default theme, the 1.x look: an unselected tab (a `Selectable`) is
 * its text in a single-line box drawn in the component's current style; the selected one (a
 * label, so not `Selectable`) is a flat block in the palette's selection colors with the text
 * where the boxed text sits, so the two line up.
 */
internal class DefaultTabRenderer : ComponentRenderer<Component> {

    override fun render(drawWindow: DrawWindow, context: ComponentRenderContext<Component>) {
        val component = context.component
        val text = (component as? TextOverride)?.text ?: ""
        val selected = (component as? Selectable)?.isSelected ?: true
        if (selected) {
            val palette = context.palette
            val style = styleSet {
                backgroundColor = palette.selection
                foregroundColor = palette.onSelection
            }
            drawWindow.fill(Tile.defaultTile().withStyle(style))
            drawWindow.drawText(text, style, drawWindow.size, Position.OFFSET_1X1)
        } else {
            val style = context.currentStyle
            drawWindow.fill(Tile.defaultTile().withStyle(style))
            drawWindow.drawBox(style)
            val (width, height) = drawWindow.size
            if (width > 2 && height > 2) {
                drawWindow.drawText(text, style, Size(width - 2, height - 2), Position.OFFSET_1X1)
            }
        }
    }

    private fun DrawWindow.drawBox(style: StyleSet) {
        val (width, height) = size
        if (width < 2 || height < 2) return
        val box = BoxType.SINGLE
        put(box.topLeft, style, 0, 0)
        put(box.topRight, style, width - 1, 0)
        put(box.bottomLeft, style, 0, height - 1)
        put(box.bottomRight, style, width - 1, height - 1)
        for (x in 1 until width - 1) {
            put(box.horizontal, style, x, 0)
            put(box.horizontal, style, x, height - 1)
        }
        for (y in 1 until height - 1) {
            put(box.vertical, style, 0, y)
            put(box.vertical, style, width - 1, y)
        }
    }

    private fun DrawWindow.put(char: Char, style: StyleSet, x: Int, y: Int) =
        draw(characterTile { character = char; styleSet = style }, Position(x, y))

    private fun DrawWindow.drawText(text: String, style: StyleSet, area: Size, position: Position) {
        if (area.width <= 0 || area.height <= 0) return
        draw(
            tileComposite = characterTileString {
                this.text = text
                size = area
                styleSet = style
            },
            drawPosition = position
        )
    }
}
