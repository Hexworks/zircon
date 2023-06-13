package org.hexworks.zircon.internal.component.renderer

import org.hexworks.zircon.api.component.ScrollBar
import org.hexworks.zircon.api.component.data.ComponentState
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.impl.DrawWindow

open class VerticalScrollBarRenderer internal constructor() : ComponentRenderer<ScrollBar> {
    open val aboveBarCharacter: Char = ' '
    open val belowBarCharacter: Char = ' '
    open val barCharacter: Char = ' '

    final override fun render(drawWindow: DrawWindow, context: ComponentRenderContext<ScrollBar>) {
        val defaultStyleSet = context.componentStyle.fetchStyleFor(ComponentState.DEFAULT)
        val invertedDefaultStyleSet = defaultStyleSet
            .withBackgroundColor(defaultStyleSet.foregroundColor)
            .withForegroundColor(defaultStyleSet.backgroundColor)
        val disabledStyleSet = context.componentStyle.fetchStyleFor(ComponentState.DISABLED)

        val lowBarPosition = context.component.currentStep
        val highBarPosition = lowBarPosition + context.component.barSizeInSteps - 1
        val totalScrollBarHeight = context.component.contentSize.height

        drawWindow.applyStyle(context.currentStyle)

        (0 until totalScrollBarHeight).forEach { idx ->
            when {
                idx < lowBarPosition -> drawWindow.draw(
                    Tile.createCharacterTile(aboveBarCharacter, disabledStyleSet),
                    Position.create(0, idx)
                )
                idx > highBarPosition -> drawWindow.draw(
                    Tile.createCharacterTile(belowBarCharacter, disabledStyleSet),
                    Position.create(0, idx)
                )
                else -> drawWindow.draw(
                    Tile.createCharacterTile(barCharacter, invertedDefaultStyleSet),
                    Position.create(0, idx)
                )
            }
        }
    }
}
