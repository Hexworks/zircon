package org.hexworks.zircon.internal.component.renderer

import org.hexworks.zircon.api.Positions
import org.hexworks.zircon.api.component.data.ComponentState
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.Symbols
import org.hexworks.zircon.api.graphics.impl.SubTileGraphics
import org.hexworks.zircon.internal.component.impl.DefaultSlider

class DefaultSliderRenderer : ComponentRenderer<DefaultSlider> {

    override fun render(tileGraphics: SubTileGraphics, context: ComponentRenderContext<DefaultSlider>) {
        val currentStyleSet = context.componentStyle.currentStyle()
        tileGraphics.applyStyle(currentStyleSet)

        val defaultStyleSet = context.componentStyle.fetchStyleFor(ComponentState.DEFAULT)
        val invertedDefaultStyleSet = defaultStyleSet
                .withBackgroundColor(defaultStyleSet.foregroundColor)
                .withForegroundColor(defaultStyleSet.backgroundColor)
        val currentValueState = context.component.getCurrentValueState()
        val label = currentValueState.actualValue.toString()
        val cursorPosition = currentValueState.width + 1
        val barWidth = context.component.numberOfSteps + 1
        val additionalWidth = context.component.additionalWidthNeeded
        val totalWidth = barWidth + additionalWidth

        tileGraphics.setTileAt(Positions.create(0,0), Tile.createCharacterTile(Symbols.TRIANGLE_LEFT_POINTING_BLACK, currentStyleSet))
        (1..barWidth + 1).forEach { idx ->
            when {
                idx == cursorPosition -> tileGraphics.setTileAt(Positions.create(idx, 0), Tile.createCharacterTile(Symbols.DOUBLE_LINE_VERTICAL, defaultStyleSet))
                idx < cursorPosition -> tileGraphics.setTileAt(Positions.create(idx, 0), Tile.createCharacterTile(' ', invertedDefaultStyleSet))
                else -> tileGraphics.setTileAt(Positions.create(idx, 0), Tile.createCharacterTile(' ', defaultStyleSet))
            }
        }
        tileGraphics.setTileAt(Positions.create(barWidth + 1, 0), Tile.createCharacterTile(Symbols.TRIANGLE_RIGHT_POINTING_BLACK, currentStyleSet))

        (1 until (additionalWidth - 2)).forEach {idx ->
            var char = ' '
            if (idx <= label.length) {
                char = label[label.length - (idx)]
            }
            tileGraphics.setTileAt(Positions.create(totalWidth - (idx + 1),0), Tile.createCharacterTile(char, defaultStyleSet))
        }
    }
}