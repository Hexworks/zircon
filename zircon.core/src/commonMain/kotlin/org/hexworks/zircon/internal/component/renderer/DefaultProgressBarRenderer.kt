package org.hexworks.zircon.internal.component.renderer

import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.graphics.impl.DrawWindow
import org.hexworks.zircon.internal.component.impl.DefaultProgressBar

class DefaultProgressBarRenderer : ComponentRenderer<DefaultProgressBar> {

    override fun render(drawWindow: DrawWindow, context: ComponentRenderContext<DefaultProgressBar>) {

        val currentStyleSet = context.currentStyle
        val progressBarState = context.component.getProgressBarState()
        drawWindow.applyStyle(currentStyleSet)

        val invertedStyleSet = currentStyleSet
            .withBackgroundColor(currentStyleSet.foregroundColor)
            .withForegroundColor(currentStyleSet.backgroundColor)

        (0 until progressBarState.currentProgression).forEach { idx ->
            drawWindow.draw(characterTile {
                character = ' '
                styleSet = invertedStyleSet
            }, Position.create(idx, 0))
        }

        if (context.component.displayPercentValueOfProgress) {
            val style = invertedStyleSet.withForegroundColor(context.theme.primaryBackgroundColor)
            val percentValue = progressBarState.currentProgressInPercent.toString().padStart(3)
            val text = "$percentValue%"
            val start = drawWindow.width / 2 - 2
            repeat(text.length) { idx ->
                val pos = Position.create(start + idx, 0)
                val char = text[idx]
                val tile = characterTile {
                    character = char
                    styleSet = if (pos.x > progressBarState.currentProgression) currentStyleSet else style
                }
                drawWindow.draw(tile, pos)
            }
        }
    }
}
