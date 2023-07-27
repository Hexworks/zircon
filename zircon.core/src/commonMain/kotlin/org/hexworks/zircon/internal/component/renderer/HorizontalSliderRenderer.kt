package org.hexworks.zircon.internal.component.renderer

import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.component.Slider
import org.hexworks.zircon.api.component.data.ComponentState
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.graphics.Symbols.DOUBLE_LINE_VERTICAL
import org.hexworks.zircon.api.graphics.impl.DrawWindow

@Suppress("DuplicatedCode")
class HorizontalSliderRenderer : ComponentRenderer<Slider> {

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
                    characterTile {
                        character = DOUBLE_LINE_VERTICAL
                        styleSet = context.currentStyle
                    }, Position.create(idx, 0)
                )

                idx < cursorPosition -> drawWindow.draw(
                    characterTile {
                        character = ' '
                        styleSet = invertedDefaultStyleSet
                    },
                    Position.create(idx, 0)
                )

                else -> drawWindow.draw(characterTile {
                    character = ' '
                    styleSet = disabledStyleSet
                }, Position.create(idx, 0))
            }
        }
    }
}
