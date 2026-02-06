package org.hexworks.zircon.internal.component.renderer

import korlibs.math.isOdd
import korlibs.math.toIntCeil
import korlibs.math.toIntFloor
import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.builder.data.position
import org.hexworks.zircon.api.builder.data.size
import org.hexworks.zircon.api.builder.data.withStyleSet
import org.hexworks.zircon.api.builder.graphics.tileImage
import org.hexworks.zircon.api.builder.graphics.withFiller
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.graphics.Symbols
import org.hexworks.zircon.api.graphics.impl.DrawWindow
import org.hexworks.zircon.internal.component.impl.DefaultVerticalScrollBar

class DefaultVerticalScrollBarRenderer internal constructor() : ComponentRenderer<DefaultVerticalScrollBar> {

    override fun render(drawWindow: DrawWindow, context: ComponentRenderContext<DefaultVerticalScrollBar>) {
        // 📘 Note that we know that a vertical scroll bar will have a width of `1` so we don't check it

        val (component, _, style, theme) = context
        val contentHeight = component.contentHeight
        val scrollY = component.scrollY

        // 📘 this is the total height of the content and the viewport
        // it is possible that we're viewing beyond the actual content
        // for example when deleting text and only seeing white space
        // in most of the editor box
        val totalHeight = component.actualHeight + contentHeight - scrollY

        val contentRatio = component.contentRatioY
        val scrollPercent = scrollY.toDouble().div(totalHeight)
        // btn has to be at least 1 in size
        val buttonHeight = maxOf(
            contentHeight
                .toDouble()
                .div(contentRatio).toIntCeil(), 1
        )
        val scrollPos = minOf(contentHeight.times(scrollPercent).toIntCeil(), contentHeight - buttonHeight)

        val needsScrollButton = scrollY > 0


        val barTile = characterTile {
            +Symbols.SINGLE_LINE_VERTICAL
            withStyleSet {
                foregroundColor = theme.secondaryForegroundColor
                backgroundColor = theme.secondaryBackgroundColor
            }
        }

        drawWindow.fill(barTile)

        if (needsScrollButton) {
            val button = tileImage {
                size = size {
                    width = 1
                    height = buttonHeight
                }
                withFiller {
                    +' '
                    styleSet = style
                }

                tiles = mapOf(position {
                    x = 0
                    y = buttonHeight / 2
                } to characterTile {
                    +Symbols.TRIPLE_BAR
                    styleSet = style
                })
            }

            drawWindow.draw(button, position {
                x = 0
                y = scrollPos
            })
        }
    }
}

private fun Double.toButtonHeight(): Int {
    val down = toIntFloor()
    val up = toIntCeil()
    return if (down == up) {
        if (down.isOdd) down else down - 1
    } else {
        if (down.isOdd) down else up
    }
}
