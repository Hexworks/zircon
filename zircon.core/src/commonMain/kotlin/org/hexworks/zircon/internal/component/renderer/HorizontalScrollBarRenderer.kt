package org.hexworks.zircon.internal.component.renderer

import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.component.ScrollBar
import org.hexworks.zircon.api.component.data.ComponentState
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.graphics.impl.DrawWindow

@Suppress("DuplicatedCode")
class HorizontalScrollBarRenderer : ComponentRenderer<ScrollBar> {

    override fun render(drawWindow: DrawWindow, context: ComponentRenderContext<ScrollBar>) {
        drawWindow.applyStyle(context.currentStyle)

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
                idx < lowBarPosition -> drawWindow.draw(
                    characterTile {
                        character = ' '
                        styleSet = disabledStyleSet
                    },
                    Position.create(idx, 0)
                )

                idx > highBarPosition -> drawWindow.draw(
                    characterTile {
                        character = ' '
                        styleSet = disabledStyleSet
                    },
                    Position.create(idx, 0)
                )

                else -> drawWindow.draw(
                    characterTile {
                        character = ' '
                        styleSet = invertedDefaultStyleSet
                    },
                    Position.create(idx, 0)
                )
            }
        }
    }
}
