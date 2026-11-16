package org.hexworks.zircon.internal.component.theme.neobrutalism

import org.hexworks.zircon.api.behavior.Boundable
import org.hexworks.zircon.api.behavior.extensions.height
import org.hexworks.zircon.api.behavior.extensions.width
import org.hexworks.zircon.api.behavior.extensions.x
import org.hexworks.zircon.api.behavior.extensions.y
import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.color.Color
import org.hexworks.zircon.api.component.data.ComponentState
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.extensions.isEmpty
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.graphics.DrawWindow
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.graphics.extensions.drawBox

/**
 * The hard offset shadow of Neobrutalism.
 */
public enum class NeoShadow {
    NONE,
    /** 2px: a thin column and a thin row, the corner empty. */
    THIN,
    /** 4px: a half column, a half row and a quadrant corner. */
    THICK,
    /** 8px: one full cell. */
    FULL,
}

/**
 * A heavy box with a [NeoShadow] offset one cell from the top left, which is what makes the
 * component read as lifted. The shadow takes one column and one row of the component.
 *
 * With [hoverShift] the box moves one column right and drops the shadow while the component
 * is highlighted, like HyperUI's pressed button; the content the component renderer already
 * drew is moved along, which is why this is one decoration and not a box plus a shadow.
 *
 * With [divider] a `┣━━┫` line is drawn under the first content row and that row is filled
 * with the accent: the title bar of a retro window.
 *
 * The border takes the component's current foreground (an alert's severity color), the
 * shadow is always ink. Disabled components lose the shadow.
 */
public class NeoFrameDecoration(
    private val palette: NeobrutalismPaletteProvider,
    private val shadow: NeoShadow = NeoShadow.THICK,
    private val boxType: BoxType = BoxType.HEAVY,
    private val hoverShift: Boolean = false,
    private val divider: Boolean = false,
) : ComponentDecorationRenderer {

    private val hasShadow = shadow != NeoShadow.NONE

    override val offset: Position = Position.OFFSET_1X1

    override val occupiedSize: Size = if (hasShadow) Size(3, 3) else Size(2, 2)

    override fun render(drawWindow: DrawWindow, context: ComponentDecorationRenderContext) {
        val size = drawWindow.size
        if (size.width < 2 || size.height < 2) return
        val state = context.component.componentState
        val style = context.currentStyle
        val colors = palette.paletteFor(context.component)
        val ink = colors?.ink ?: context.defaultStyle.foregroundColor
        val shifted = hoverShift && hasShadow && (state == ComponentState.HIGHLIGHTED || state == ComponentState.ACTIVE)
        val boxSize = if (hasShadow) Size(size.width - 1, size.height - 1) else size
        val boxX = if (shifted) 1 else 0

        if (shifted) shiftContentRight(drawWindow, boxSize)
        drawWindow.fillEmptyCells(style, Boundable.create(Position(boxX + 1, 1), Size(boxSize.width - 2, boxSize.height - 2)))
        drawWindow.drawBox(boxType = boxType, style = style, position = Position(boxX, 0), size = boxSize)
        if (divider && boxSize.height > 3) drawDivider(drawWindow, boxX, boxSize, style, colors?.accent)
        if (hasShadow && !shifted && state != ComponentState.DISABLED) drawShadow(drawWindow, boxSize, ink)
    }

    private fun shiftContentRight(drawWindow: DrawWindow, boxSize: Size) {
        // Content lives inside the box: columns 1..w-2, rows 1..h-2. Move it one column right,
        // from the right so nothing is read after it was overwritten; the left column is cleared.
        for (y in 1 until boxSize.height - 1) {
            for (x in boxSize.width - 1 downTo 2) {
                drawWindow.draw(drawWindow.getTileAtOrNull(Position(x - 1, y)) ?: EMPTY, Position(x, y))
            }
            drawWindow.draw(EMPTY, Position(1, y))
        }
    }

    private fun drawDivider(drawWindow: DrawWindow, boxX: Int, boxSize: Size, style: StyleSet, accent: Color?) {
        val y = 2
        val right = boxX + boxSize.width - 1
        drawWindow.draw(characterTile { character = boxType.connectorRight; styleSet = style }, Position(boxX, y))
        for (x in boxX + 1 until right) {
            drawWindow.draw(characterTile { character = boxType.horizontal; styleSet = style }, Position(x, y))
        }
        drawWindow.draw(characterTile { character = boxType.connectorLeft; styleSet = style }, Position(right, y))
        if (accent != null) {
            drawWindow.transform { pos, tile ->
                if (pos.y == 1 && pos.x in (boxX + 1) until right && tile is CharacterTile) tile.withBackgroundColor(accent) else tile
            }
        }
    }

    private fun drawShadow(drawWindow: DrawWindow, boxSize: Size, ink: Color) {
        val right = boxSize.width
        val bottom = boxSize.height
        fun put(char: Char, x: Int, y: Int) = drawWindow.draw(
            characterTile {
                character = char
                styleSet = StyleSet.defaultStyle().withForegroundColor(ink).withBackgroundColor(Color.TRANSPARENT)
            },
            Position(x, y)
        )
        when (shadow) {
            NeoShadow.THIN -> {
                for (y in 1 until bottom) put('▎', right, y)
                for (x in 1 until right) put('▔', x, bottom)
            }
            NeoShadow.THICK -> {
                for (y in 1 until bottom) put('▌', right, y)
                for (x in 1 until right) put('▀', x, bottom)
                put('▘', right, bottom)
            }
            NeoShadow.FULL -> {
                for (y in 1..bottom) put('█', right, y)
                for (x in 1 until right) put('█', x, bottom)
            }
            NeoShadow.NONE -> Unit
        }
    }

    public companion object {

        /**
         * Draws a blank in [style] on every cell of [area] nothing drew on: the padding around
         * the content takes the component's (hover) background this way.
         */
        internal fun DrawWindow.fillEmptyCells(style: StyleSet, area: Boundable) {
            val blank = characterTile { character = ' '; styleSet = style }
            for (y in area.y until area.y + area.height) {
                for (x in area.x until area.x + area.width) {
                    val pos = Position(x, y)
                    val tile = getTileAtOrNull(pos)
                    if (tile == null || tile.isEmpty) draw(blank, pos)
                }
            }
        }

        private val EMPTY = characterTile {
            character = ' '
            styleSet = StyleSet.empty()
        }
    }
}
