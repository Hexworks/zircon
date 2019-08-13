package org.hexworks.zircon.internal.component.renderer

import org.hexworks.zircon.api.Positions
import org.hexworks.zircon.api.component.ScrollBar
import org.hexworks.zircon.api.component.data.ComponentState
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.impl.SubTileGraphics

class HorizontalScrollBarRenderer : ComponentRenderer<ScrollBar> {

    override fun render(tileGraphics: SubTileGraphics, context: ComponentRenderContext<ScrollBar>) {
        val style = context.componentStyle.currentStyle()
        tileGraphics.applyStyle(style)

        val defaultStyleSet = context.componentStyle.fetchStyleFor(ComponentState.DEFAULT)
        val invertedDefaultStyleSet = defaultStyleSet
                .withBackgroundColor(defaultStyleSet.foregroundColor)
                .withForegroundColor(defaultStyleSet.backgroundColor)
        val disabledStyleSet = context.componentStyle.fetchStyleFor(ComponentState.DISABLED)

        val lowBarPosition = context.component.currentStep
        val highBarPosition = lowBarPosition + context.component.barSizeInSteps - 1
        val totalScrollBarWidth = context.component.contentSize.width

        (0..totalScrollBarWidth).forEach { idx ->
            when {
                idx < lowBarPosition -> tileGraphics.setTileAt(Positions.create(idx, 0), Tile.createCharacterTile(' ', disabledStyleSet))
                idx > highBarPosition  -> tileGraphics.setTileAt(Positions.create(idx, 0), Tile.createCharacterTile(' ', disabledStyleSet))
                else -> tileGraphics.setTileAt(Positions.create(idx, 0), Tile.createCharacterTile(' ', invertedDefaultStyleSet))
            }
        }
    }
}