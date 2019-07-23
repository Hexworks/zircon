package org.hexworks.zircon.internal.component.renderer

import org.hexworks.zircon.internal.component.impl.DefaultVerticalSlider
import org.hexworks.zircon.api.Positions
import org.hexworks.zircon.api.component.data.ComponentState
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.Symbols
import org.hexworks.zircon.api.graphics.impl.SubTileGraphics


class DefaultVerticalSliderRenderer : ComponentRenderer<DefaultVerticalSlider> {

    override fun render(tileGraphics: SubTileGraphics, context: ComponentRenderContext<DefaultVerticalSlider>) {
        val currentStyleSet = context.componentStyle.currentStyle()
        tileGraphics.applyStyle(currentStyleSet)

        val defaultStyleSet = context.componentStyle.fetchStyleFor(ComponentState.DEFAULT)
        val invertedDefaultStyleSet = defaultStyleSet
                .withBackgroundColor(defaultStyleSet.foregroundColor)
                .withForegroundColor(defaultStyleSet.backgroundColor)
        val disabledStyleSet = context.componentStyle.fetchStyleFor(ComponentState.DISABLED)

        val currentValueState = context.component.getCurrentValueState()
        val label = currentValueState.actualValue.toString()
        val cursorPosition = currentValueState.height + 1
        val barHeight = context.component.numberOfSteps + 1
        val additionalHeight = context.component.additionalHeightNeeded
        val totalHeight = barHeight + additionalHeight

        tileGraphics.setTileAt(Positions.create(0,0), Tile.createCharacterTile(Symbols.TRIANGLE_UP_POINTING_BLACK, currentStyleSet))
        (1..barHeight + 1).forEach { idx ->
            when {
                idx == cursorPosition -> tileGraphics.setTileAt(Positions.create(0, idx), Tile.createCharacterTile(Symbols.DOUBLE_LINE_HORIZONTAL, defaultStyleSet))
                idx < cursorPosition -> tileGraphics.setTileAt(Positions.create(0, idx), Tile.createCharacterTile(' ', invertedDefaultStyleSet))
                else -> tileGraphics.setTileAt(Positions.create(0, idx), Tile.createCharacterTile(' ', disabledStyleSet))
            }
        }
        tileGraphics.setTileAt(Positions.create( 0, barHeight + 1), Tile.createCharacterTile(Symbols.TRIANGLE_DOWN_POINTING_BLACK, currentStyleSet))

        (1 until (additionalHeight - 2)).forEach {idx ->
            var char = ' '
            if (idx <= label.length) {
                char = label[label.length - (idx)]
            }
            tileGraphics.setTileAt(Positions.create(0, totalHeight - (idx + 1)), Tile.createCharacterTile(char, defaultStyleSet))
        }
    }
}