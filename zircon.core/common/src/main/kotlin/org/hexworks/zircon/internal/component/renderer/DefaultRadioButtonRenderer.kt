package org.hexworks.zircon.internal.component.renderer

import org.hexworks.zircon.api.CharacterTileStrings
import org.hexworks.zircon.api.Positions
import org.hexworks.zircon.api.Tiles
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.internal.component.impl.DefaultRadioButton
import org.hexworks.zircon.internal.component.impl.DefaultRadioButton.RadioButtonState.NOT_SELECTED
import org.hexworks.zircon.internal.component.impl.DefaultRadioButton.RadioButtonState.PRESSED
import org.hexworks.zircon.internal.component.impl.DefaultRadioButton.RadioButtonState.SELECTED
import kotlin.math.max

class DefaultRadioButtonRenderer : ComponentRenderer<DefaultRadioButton> {

    override fun render(tileGraphics: TileGraphics, context: ComponentRenderContext<DefaultRadioButton>) {
        val checkBoxState = context.component.radioButtonState
        val text = context.component.text
        val maxTextLength = max(0, tileGraphics.size.width - BUTTON_WIDTH - 1)
        val clearedText = if (text.length > maxTextLength) {
            text.substring(0, maxTextLength - 3).plus(ELLIPSIS)
        } else {
            text
        }
        val finalText = "${STATES[checkBoxState] ?: error("")} $clearedText"
        tileGraphics.draw(CharacterTileStrings
                .newBuilder()
                .withText(finalText)
                .withSize(tileGraphics.size)
                .build())
        (finalText.length until tileGraphics.width).forEach { idx ->
            tileGraphics.draw(Tiles.empty(), Positions.create(idx, 0))
        }
        tileGraphics.applyStyle(context.currentStyle)
    }

    companion object {
        private const val ELLIPSIS = "..."
        private const val PRESSED_BUTTON = "<o>"
        private const val SELECTED_BUTTON = "<O>"
        private const val NOT_SELECTED_BUTTON = "< >"
        private const val BUTTON_WIDTH = NOT_SELECTED_BUTTON.length

        private val STATES = mapOf(
                Pair(PRESSED, PRESSED_BUTTON),
                Pair(SELECTED, SELECTED_BUTTON),
                Pair(NOT_SELECTED, NOT_SELECTED_BUTTON))
    }
}
