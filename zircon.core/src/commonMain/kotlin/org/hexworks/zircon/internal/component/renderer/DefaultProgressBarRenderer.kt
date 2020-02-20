package org.hexworks.zircon.internal.component.renderer

import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.internal.component.impl.DefaultProgressBar

class DefaultProgressBarRenderer : ComponentRenderer<DefaultProgressBar> {

    override fun render(tileGraphics: TileGraphics, context: ComponentRenderContext<DefaultProgressBar>) {

        val currentStyleSet = context.currentStyle
        val progressBarState = context.component.getProgressBarState()
        tileGraphics.applyStyle(currentStyleSet)

        val invertedStyleSet = currentStyleSet
                .withBackgroundColor(currentStyleSet.foregroundColor)
                .withForegroundColor(currentStyleSet.backgroundColor)

        (0 until progressBarState.currentProgression).forEach { idx ->
            tileGraphics.draw(Tile.createCharacterTile(' ', invertedStyleSet), Position.create(idx, 0))
        }

        if (context.component.displayPercentValueOfProgress) {
            val style = invertedStyleSet.withForegroundColor(context.theme.primaryBackgroundColor)
            val percentValue = progressBarState.currentProgressInPercent.toString().padStart(3)
            val text = "$percentValue%"
            val start = tileGraphics.width / 2 - 2
            repeat(text.length) { idx ->
                val pos = Position.create(start + idx, 0)
                val char = text[idx]
                val tile = Tile.createCharacterTile(
                        character = char,
                        style = if (pos.x > progressBarState.currentProgression) currentStyleSet else style)
                tileGraphics.draw(tile, pos)
            }
        }
    }
}
