package org.hexworks.zircon.internal.component.renderer

import org.hexworks.zircon.api.Positions
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.impl.SubTileGraphics
import org.hexworks.zircon.internal.component.impl.DefaultSlider

class DefaultSliderRenderer : ComponentRenderer<DefaultSlider> {

    override fun render(tileGraphics: SubTileGraphics, context: ComponentRenderContext<DefaultSlider>) {
        val currentStyleSet = context.componentStyle.currentStyle()
        tileGraphics.applyStyle(currentStyleSet)
        val invertedStyleSet = currentStyleSet
                .withBackgroundColor(currentStyleSet.foregroundColor)
                .withForegroundColor(currentStyleSet.backgroundColor)
        val currentValueState = context.component.getCurrentValueState()

        (0 until currentValueState.width).forEach { idx ->
            tileGraphics.setTileAt(Positions.create(idx, 0), Tile.createCharacterTile(' ', invertedStyleSet))
        }
    }
}