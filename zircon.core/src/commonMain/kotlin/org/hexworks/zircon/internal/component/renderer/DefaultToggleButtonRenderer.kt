package org.hexworks.zircon.internal.component.renderer

import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.builder.data.withStyleSet
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.Symbols.SINGLE_LINE_HORIZONTAL
import org.hexworks.zircon.api.graphics.Symbols.TRIPLE_BAR
import org.hexworks.zircon.api.graphics.impl.DrawWindow
import org.hexworks.zircon.internal.component.impl.DefaultToggleButton
import kotlin.math.max

class DefaultToggleButtonRenderer : ComponentRenderer<DefaultToggleButton> {

    override fun render(drawWindow: DrawWindow, context: ComponentRenderContext<DefaultToggleButton>) {
        val text = context.component.text
        val maxTextLength = max(0, drawWindow.size.width - DECORATION_WIDTH)
        val clearedText = if (text.length > maxTextLength) {
            text.substring(0, max(0, maxTextLength - 3)).plus(ELLIPSIS)
        } else {
            text
        }
        drawWindow.fillWithText(
            text = "   $clearedText",
            style = context.currentStyle
        )

        val isToggled = context.component.isSelected
        val theme = context.component.theme
        val toggledBackground = ANSITileColor.GRAY.withAlpha(0)
        val untoggledBackground = ANSITileColor.GRAY.withAlpha(0)
        val bar = characterTile {
            character = SINGLE_LINE_HORIZONTAL
            withStyleSet {
                foregroundColor = theme.secondaryForegroundColor
                backgroundColor = if (isToggled) toggledBackground else untoggledBackground
            }
        }
        if (isToggled) {
            drawWindow.draw(bar, Position.create(0, 0))
            drawWindow.draw(
                tile = characterTile {
                    character = TRIPLE_BAR
                    withStyleSet {
                        foregroundColor = theme.accentColor
                        backgroundColor = toggledBackground
                    }
                },
                drawPosition = Position.create(1, 0)
            )
        } else {
            drawWindow.draw(
                tile = characterTile {
                    character = TRIPLE_BAR
                    withStyleSet {
                        foregroundColor = theme.primaryForegroundColor
                        backgroundColor = untoggledBackground
                    }
                },
                drawPosition = Position.create(0, 0)
            )
            drawWindow.draw(bar, Position.create(1, 0))
        }
        drawWindow.draw(
            tile = Tile.empty(),
            drawPosition = Position.create(2, 0)
        )
    }

    companion object {
        private const val ELLIPSIS = "..."
        const val DECORATION_WIDTH = 3
    }
}
