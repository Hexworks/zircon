package org.hexworks.zircon.internal.component.renderer

import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.internal.component.impl.DefaultRadioButton
import org.hexworks.zircon.internal.component.impl.DefaultRadioButton.RadioButtonState.NOT_SELECTED
import org.hexworks.zircon.internal.component.impl.DefaultRadioButton.RadioButtonState.PRESSED
import org.hexworks.zircon.internal.component.impl.DefaultRadioButton.RadioButtonState.SELECTED
import kotlin.math.max

@Suppress("DuplicatedCode")
class DefaultRadioButtonRenderer : ComponentRenderer<DefaultRadioButton> {

    override fun render(tileGraphics: TileGraphics, context: ComponentRenderContext<DefaultRadioButton>) {
        val state = context.component.state
        val text = context.component.text
        val maxTextLength = max(0, tileGraphics.size.width - DECORATION_WIDTH)
        val clearedText = if (text.length > maxTextLength) {
            text.substring(0, max(0, maxTextLength - 3)).plus(ELLIPSIS)
        } else {
            text
        }
        tileGraphics.fillWithText(
            text = "${STATES.getValue(state)} $clearedText",
            style = context.currentStyle
        )
    }

    companion object {
        private const val ELLIPSIS = "..."
        private const val PRESSED_BUTTON = "<o>"
        private const val SELECTED_BUTTON = "<O>"
        private const val NOT_SELECTED_BUTTON = "< >"
        const val DECORATION_WIDTH = NOT_SELECTED_BUTTON.length + 1

        private val STATES = mapOf(
            Pair(PRESSED, PRESSED_BUTTON),
            Pair(SELECTED, SELECTED_BUTTON),
            Pair(NOT_SELECTED, NOT_SELECTED_BUTTON)
        )
    }
}
