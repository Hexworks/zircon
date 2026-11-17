package org.hexworks.zircon.internal.component.renderer

import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Position
import kotlin.math.roundToInt
import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.builder.data.withStyleSet
import org.hexworks.zircon.api.builder.graphics.tileImage
import org.hexworks.zircon.api.builder.graphics.withFiller
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.graphics.Symbols
import org.hexworks.zircon.api.graphics.DrawWindow
import org.hexworks.zircon.internal.component.impl.DefaultVerticalScrollBar
import org.hexworks.zircon.api.component.theme.background
import org.hexworks.zircon.api.component.theme.textMuted

public class DefaultVerticalScrollBarRenderer internal constructor() : ComponentRenderer<DefaultVerticalScrollBar> {

    override fun render(drawWindow: DrawWindow, context: ComponentRenderContext<DefaultVerticalScrollBar>) {
        val (component, _, style) = context
        val palette = context.palette
        val barHeight = drawWindow.size.height
        val visible = component.scrollable.visibleSize.height
        val actual = component.actualHeight
        val barTile = characterTile {
            +Symbols.SINGLE_LINE_VERTICAL
            withStyleSet {
                foregroundColor = palette.textMuted
                backgroundColor = palette.background
            }
        }
        drawWindow.fill(barTile)
        if (actual <= visible || barHeight <= 0) return
        // The thumb spans the visible share of the content and travels the rest of the bar.
        val thumbHeight = (barHeight * visible / actual).coerceIn(1, barHeight)
        val maxOffset = actual - visible
        val thumbPos = (component.scrollY.toDouble() / maxOffset * (barHeight - thumbHeight)).roundToInt()
        val thumb = tileImage {
            size = Size(1, thumbHeight)
            withFiller {
                +' '
                styleSet = style
            }
            tiles = mapOf(Position(0, thumbHeight / 2) to characterTile {
                +Symbols.TRIPLE_BAR
                styleSet = style
            })
        }
        drawWindow.draw(thumb, Position(0, thumbPos))
    }
}

