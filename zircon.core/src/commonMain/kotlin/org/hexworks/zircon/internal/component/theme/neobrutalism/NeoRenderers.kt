package org.hexworks.zircon.internal.component.theme.neobrutalism

import org.hexworks.zircon.api.builder.graphics.styleSet
import org.hexworks.zircon.api.component.data.Orientation
import org.hexworks.zircon.api.behavior.Selectable
import org.hexworks.zircon.api.behavior.TextOverride
import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.component.CheckBox
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.Icon
import org.hexworks.zircon.api.component.ProgressBar
import org.hexworks.zircon.api.component.data.ComponentState
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.component.theme.onSelection
import org.hexworks.zircon.api.component.theme.selection
import org.hexworks.zircon.api.component.theme.surface
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.graphics.DrawWindow
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.graphics.TextWrap
import kotlin.math.roundToInt

private fun DrawWindow.put(char: Char, style: StyleSet, x: Int, y: Int) =
    draw(characterTile { character = char; styleSet = style }, Position(x, y))

private fun DrawWindow.fillWith(style: StyleSet) = fill(characterTile { character = ' '; styleSet = style })

/**
 * Draws text (word-wrapped) or an icon's glyph with the component's current style, so one
 * role can cover the label and the icon of a fragment.
 */
public class NeoContentRenderer : ComponentRenderer<Component> {
    override fun render(drawWindow: DrawWindow, context: ComponentRenderContext<Component>) {
        val component = context.component
        val style = context.currentStyle
        when (component) {
            is Icon -> {
                drawWindow.fillWith(style)
                val char = (component.icon as? CharacterTile)?.character ?: ' '
                drawWindow.put(char, style, 0, 0)
            }
            is TextOverride -> drawWindow.fillWithText(component.text, style, TextWrap.WORD_WRAP)
            else -> drawWindow.fillWith(style)
        }
    }
}

/**
 * `☐  Label`, and an inverted `✓` cell when checked. One cell, no shadow.
 */
public class NeoCheckBoxRenderer : ComponentRenderer<CheckBox> {
    override fun render(drawWindow: DrawWindow, context: ComponentRenderContext<CheckBox>) {
        val component = context.component
        val style = context.currentStyle
        drawWindow.fillWith(style)
        val checked = component.isSelected
        val boxStyle = if (checked) style.withForegroundColor(style.backgroundColor).withBackgroundColor(style.foregroundColor) else style
        val checkedStyle = if (checked && style.backgroundColor.isTransparent) {
            style.withForegroundColor(context.palette.surface).withBackgroundColor(style.foregroundColor)
        } else boxStyle
        drawWindow.put(if (checked) '✓' else '☐', checkedStyle, 0, 0)
        component.text.take(maxOf(0, drawWindow.size.width - GAP)).forEachIndexed { i, c ->
            drawWindow.put(c, style, GAP + i, 0)
        }
    }

    public companion object {
        /** The box, then two cells before the label. */
        public const val GAP: Int = 3
    }
}

/**
 * The fill in the current foreground, ending on a `▌` half cell so odd percentages land.
 */
public class NeoProgressBarRenderer : ComponentRenderer<ProgressBar> {
    override fun render(drawWindow: DrawWindow, context: ComponentRenderContext<ProgressBar>) {
        val component = context.component
        val style = context.currentStyle
        drawWindow.fillWith(style)
        val width = drawWindow.size.width
        val ratio = (component.progress / component.range).coerceIn(0.0, 1.0)
        val halves = (ratio * width * 2).roundToInt()
        val full = halves / 2
        val fillStyle = style.withBackgroundColor(style.foregroundColor)
        for (x in 0 until full) drawWindow.put(' ', fillStyle, x, 0)
        if (halves % 2 == 1 && full < width) drawWindow.put('▌', style, full, 0)
    }
}

/**
 * A tab, read from the component: selected when it is not a `Selectable` (the label a
 * selected tab turns into) or a selected one, vertical when its [Orientation] says so.
 *
 * Unselected: the label on the middle row and the bar's line along the bottom row (or the
 * right column when vertical); hovered, every cell inverts. Selected: a heavy box open towards
 * the content in the palette's accent, its corners joining the bar's line (`┛ ┗` under a
 * horizontal tab, `┛` above and `┓` below a vertical one).
 */
public class NeoTabRenderer : ComponentRenderer<Component> {
    override fun render(drawWindow: DrawWindow, context: ComponentRenderContext<Component>) {
        val component = context.component
        val vertical = component.orientation == Orientation.VERTICAL
        val selected = (component as? Selectable)?.isSelected ?: true
        val text = (component as? TextOverride)?.text ?: ""
        val (w, h) = drawWindow.size
        if (selected) {
            val palette = component.componentPalette
            val style = styleSet { foregroundColor = palette.onSelection; backgroundColor = palette.selection }
            drawWindow.fillWith(style)
            if (w < 2 || h < 3) return
            val box = BoxType.HEAVY
            if (vertical) {
                // ┏━━━┛ / ┃ text  / ┗━━━┓ : the right column carries the bar's line.
                drawWindow.put(box.topLeft, style, 0, 0)
                for (x in 1 until w - 1) drawWindow.put(box.horizontal, style, x, 0)
                drawWindow.put(box.bottomRight, style, w - 1, 0)
                drawWindow.put(box.vertical, style, 0, 1)
                drawWindow.put(box.bottomLeft, style, 0, h - 1)
                for (x in 1 until w - 1) drawWindow.put(box.horizontal, style, x, h - 1)
                drawWindow.put(box.topRight, style, w - 1, h - 1)
                drawCentered(drawWindow, text, style, w, 1, lastColumnFree = true)
            } else {
                drawWindow.put(box.topLeft, style, 0, 0)
                for (x in 1 until w - 1) drawWindow.put(box.horizontal, style, x, 0)
                drawWindow.put(box.topRight, style, w - 1, 0)
                drawWindow.put(box.vertical, style, 0, 1)
                drawWindow.put(box.vertical, style, w - 1, 1)
                drawWindow.put(box.bottomRight, style, 0, h - 1)
                drawWindow.put(box.bottomLeft, style, w - 1, h - 1)
                drawCentered(drawWindow, text, style, w, 1, lastColumnFree = false)
            }
        } else {
            val style = context.currentStyle
            drawWindow.fillWith(style)
            drawCentered(drawWindow, text, style, w, 1, lastColumnFree = vertical)
            if (vertical) {
                for (y in 0 until h) drawWindow.put(BoxType.HEAVY.vertical, style, w - 1, y)
            } else {
                for (x in 0 until w) drawWindow.put(BoxType.HEAVY.horizontal, style, x, h - 1)
            }
        }
    }
}

/**
 * The strip of a tab bar: the line the tabs sit on, along the bottom row (or the right column
 * when the strip's [Orientation] is vertical). Tabs draw over it with their own joints.
 */
public class NeoTabBarRenderer : ComponentRenderer<Component> {
    override fun render(drawWindow: DrawWindow, context: ComponentRenderContext<Component>) {
        val style = context.currentStyle
        drawWindow.fillWith(style)
        val (w, h) = drawWindow.size
        if (context.component.orientation == Orientation.VERTICAL) {
            for (y in 0 until h) drawWindow.put(BoxType.HEAVY.vertical, style, w - 1, y)
        } else {
            for (x in 0 until w) drawWindow.put(BoxType.HEAVY.horizontal, style, x, h - 1)
        }
    }
}

private fun drawCentered(drawWindow: DrawWindow, text: String, style: StyleSet, width: Int, y: Int, lastColumnFree: Boolean) {
    val inner = if (lastColumnFree) width - 2 else width - 2
    val shown = text.take(maxOf(0, inner))
    val start = 1 + maxOf(0, (inner - shown.length) / 2)
    shown.forEachIndexed { i, c -> drawWindow.put(c, style, start + i, y) }
}

internal val ComponentState.isHovered: Boolean
    get() = this == ComponentState.HIGHLIGHTED || this == ComponentState.ACTIVE
