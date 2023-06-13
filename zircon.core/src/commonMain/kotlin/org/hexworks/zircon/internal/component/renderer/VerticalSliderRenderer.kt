package org.hexworks.zircon.internal.component.renderer

import org.hexworks.zircon.api.component.Slider
import org.hexworks.zircon.api.component.data.ComponentState
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.Symbols
import org.hexworks.zircon.api.graphics.impl.DrawWindow

@Suppress("DuplicatedCode")
class VerticalSliderRenderer : ComponentRenderer<Slider> {

    override fun render(drawWindow: DrawWindow, context: ComponentRenderContext<Slider>) {
        drawWindow.applyStyle(context.currentStyle)

        val defaultStyleSet = context.componentStyle.fetchStyleFor(ComponentState.DEFAULT)
        val invertedDefaultStyleSet = defaultStyleSet
            .withBackgroundColor(defaultStyleSet.foregroundColor)
            .withForegroundColor(defaultStyleSet.backgroundColor)
        val disabledStyleSet = context.componentStyle.fetchStyleFor(ComponentState.DISABLED)

        val cursorPosition = context.component.currentStep
        val barWidth = context.component.numberOfSteps

        (0..barWidth).forEach { idx ->
            when {
                idx == cursorPosition -> drawWindow.draw(
                    tile = Tile.createCharacterTile(Symbols.DOUBLE_LINE_HORIZONTAL, context.currentStyle),
                    drawPosition = Position.create(0, idx)
                )
                idx < cursorPosition -> drawWindow.draw(
                    tile = Tile.createCharacterTile(' ', invertedDefaultStyleSet),
                    drawPosition = Position.create(0, idx)
                )
                else -> drawWindow.draw(
                    tile = Tile.createCharacterTile(' ', disabledStyleSet),
                    drawPosition = Position.create(0, idx)
                )
            }
        }
    }
}
