package org.hexworks.zircon.internal.component.renderer

import org.hexworks.zircon.api.Positions
import org.hexworks.zircon.api.builder.data.TileBuilder
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.impl.SubTileGraphics
import org.hexworks.zircon.internal.component.impl.DefaultProgressBar

class DefaultProgressBarRenderer : ComponentRenderer<DefaultProgressBar> {

    override fun render(tileGraphics: SubTileGraphics, context: ComponentRenderContext<DefaultProgressBar>) {
        val currentStyleSet = context.componentStyle.currentStyle()
        tileGraphics.applyStyle(currentStyleSet)
        val invertedStyleSet = currentStyleSet
                .withBackgroundColor(currentStyleSet.foregroundColor)
                .withForegroundColor(currentStyleSet.backgroundColor)
        val progressBarState = context.component.getProgressBarState()

        (0 until progressBarState.width).forEach { idx ->
            tileGraphics.setTileAt(Positions.create(idx, 0), Tile.createCharacterTile(' ', invertedStyleSet))
        }

        if (context.component.displayPercentValueOfProgress) {
            val percentValue = progressBarState.currentProgressInPercent.toString().padStart(3)
            tileGraphics.putText("$percentValue%",
                    Position.create(context.component.width - 6, 0))
        }
    }
}
