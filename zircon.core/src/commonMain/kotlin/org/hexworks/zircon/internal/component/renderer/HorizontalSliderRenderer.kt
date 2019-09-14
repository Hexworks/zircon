package org.hexworks.zircon.internal.component.renderer

import org.hexworks.zircon.api.Positions
import org.hexworks.zircon.api.component.Slider
import org.hexworks.zircon.api.component.data.ComponentState
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.Symbols
import org.hexworks.zircon.api.graphics.impl.SubTileGraphics

class HorizontalSliderRenderer : ComponentRenderer<Slider> {
    override fun render(tileGraphics: SubTileGraphics, context: ComponentRenderContext<Slider>) {
        val style = context.componentStyle.currentStyle()
        tileGraphics.applyStyle(style)

        val defaultStyleSet = context.componentStyle.fetchStyleFor(ComponentState.DEFAULT)
        val invertedDefaultStyleSet = defaultStyleSet
                .withBackgroundColor(defaultStyleSet.foregroundColor)
                .withForegroundColor(defaultStyleSet.backgroundColor)
        val disabledStyleSet = context.componentStyle.fetchStyleFor(ComponentState.DISABLED)

        val cursorPosition = context.component.currentStep
        val barWidth = context.component.numberOfSteps

        (0..barWidth).forEach { idx ->
            when {
                idx == cursorPosition -> tileGraphics.setTileAt(Positions.create(idx, 0), Tile.createCharacterTile(Symbols.DOUBLE_LINE_VERTICAL, style))
                idx < cursorPosition -> tileGraphics.setTileAt(Positions.create(idx, 0), Tile.createCharacterTile(' ', invertedDefaultStyleSet))
                else -> tileGraphics.setTileAt(Positions.create(idx, 0), Tile.createCharacterTile(' ', disabledStyleSet))
            }
        }
    }
}