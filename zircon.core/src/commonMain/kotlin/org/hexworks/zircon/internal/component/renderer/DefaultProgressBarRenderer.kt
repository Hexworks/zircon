package org.hexworks.zircon.internal.component.renderer

import org.hexworks.zircon.api.CharacterTileStrings
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.internal.component.impl.DefaultProgressBar

class DefaultProgressBarRenderer : ComponentRenderer<DefaultProgressBar> {

    override fun render(tileGraphics: TileGraphics, context: ComponentRenderContext<DefaultProgressBar>) {
        val currentStyleSet = context.componentStyle.currentStyle()
        tileGraphics.applyStyle(currentStyleSet)
        val invertedStyleSet = currentStyleSet
                .withBackgroundColor(currentStyleSet.foregroundColor)
                .withForegroundColor(currentStyleSet.backgroundColor)
        val progressBarState = context.component.getProgressBarState()

        (0 until progressBarState.width).forEach { idx ->
            tileGraphics.draw(Tile.createCharacterTile(' ', invertedStyleSet), Position.create(idx, 0))
        }

        if (context.component.displayPercentValueOfProgress) {
            val percentValue = progressBarState.currentProgressInPercent.toString().padStart(3)
            val text = "$percentValue%"
            tileGraphics.draw(CharacterTileStrings
                    .newBuilder()
                    .withText(text).withSize(Size.create(text.length, 1))
                    .build(),
                    Position.create(context.component.width - 6, 0))
        }
    }
}
