package org.hexworks.zircon.internal.component.renderer

import org.hexworks.zircon.api.Positions
import org.hexworks.zircon.api.component.Panel
import org.hexworks.zircon.api.component.data.ComponentState
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.Symbols
import org.hexworks.zircon.api.graphics.impl.SubTileGraphics
import org.hexworks.zircon.internal.component.impl.DefaultSlider

class VerticalSliderGutterRenderer(private val valueCallback: () -> DefaultSlider.CurrentValueState) : ComponentRenderer<Panel> {

    override fun render(tileGraphics: SubTileGraphics, context: ComponentRenderContext<Panel>) {
        val style = context.componentStyle.currentStyle()
        tileGraphics.applyStyle(style)

        val defaultStyleSet = context.componentStyle.fetchStyleFor(ComponentState.DEFAULT)
        val invertedDefaultStyleSet = defaultStyleSet
                .withBackgroundColor(defaultStyleSet.foregroundColor)
                .withForegroundColor(defaultStyleSet.backgroundColor)
        val disabledStyleSet = context.componentStyle.fetchStyleFor(ComponentState.DISABLED)

        val currentValueState = valueCallback()
        val cursorPosition = currentValueState.steps
        val barWidth = currentValueState.maxValue

        (0..barWidth).forEach { idx ->
            when {
                idx == cursorPosition -> tileGraphics.setTileAt(Positions.create(0, idx), Tile.createCharacterTile(Symbols.DOUBLE_LINE_VERTICAL, defaultStyleSet))
                idx < cursorPosition -> tileGraphics.setTileAt(Positions.create(0, idx), Tile.createCharacterTile(' ', invertedDefaultStyleSet))
                else -> tileGraphics.setTileAt(Positions.create(0, idx), Tile.createCharacterTile(' ', disabledStyleSet))
            }
        }
    }
}