package org.hexworks.zircon.internal.component.renderer

import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.component.renderer.fillWithText
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.graphics.Symbols
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.internal.component.impl.DefaultToggleButton
import kotlin.math.max

class DefaultToggleButtonRenderer : ComponentRenderer<DefaultToggleButton> {

    override fun render(tileGraphics: TileGraphics, context: ComponentRenderContext<DefaultToggleButton>) {
        val text = context.component.text
        val maxTextLength = max(0, tileGraphics.size.width - DECORATION_WIDTH)
        val clearedText = if (text.length > maxTextLength) {
            text.substring(0, max(0, maxTextLength - 3)).plus(ELLIPSIS)
        } else {
            text
        }
        tileGraphics.fillWithText(
            text = "   $clearedText",
            style = context.currentStyle
        )

        val isToggled = context.component.isSelected
        val theme = context.component.theme
        val toggledBackground = ANSITileColor.GRAY.withAlpha(0)
        val unToggledBackground = ANSITileColor.GRAY.withAlpha(0)
        val bar = Tile.createCharacterTile(
            Symbols.SINGLE_LINE_HORIZONTAL, StyleSet.newBuilder().apply {
                foregroundColor = theme.secondaryForegroundColor
                backgroundColor = if (isToggled) toggledBackground else unToggledBackground
            }.build()
        )
        if (isToggled) {
            tileGraphics.draw(bar, Position.create(0, 0))
            tileGraphics.draw(
                tile = Tile.createCharacterTile(
                    Symbols.TRIPLE_BAR, StyleSet.newBuilder().apply {
                        foregroundColor = theme.accentColor
                        backgroundColor = toggledBackground
                    }.build()
                ),
                drawPosition = Position.create(1, 0)
            )
        } else {
            tileGraphics.draw(
                tile = Tile.createCharacterTile(
                    Symbols.TRIPLE_BAR, StyleSet.newBuilder().apply {
                        foregroundColor = theme.primaryForegroundColor
                        backgroundColor = unToggledBackground
                    }.build()
                ),
                drawPosition = Position.create(0, 0)
            )
            tileGraphics.draw(bar, Position.create(1, 0))
        }
        tileGraphics.draw(
            tile = Tile.empty(),
            drawPosition = Position.create(2, 0)
        )
    }

    companion object {
        private const val ELLIPSIS = "..."
        const val DECORATION_WIDTH = 3
    }
}
