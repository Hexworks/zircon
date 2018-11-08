package org.hexworks.zircon.internal.component.renderer

import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.graphics.impl.SubTileGraphics
import org.hexworks.zircon.internal.component.impl.DefaultRadioButton
import org.hexworks.zircon.internal.component.impl.DefaultRadioButton.RadioButtonState.*
import kotlin.math.max

class DefaultRadioButtonRenderer : ComponentRenderer<DefaultRadioButton> {

    override fun render(tileGraphics: SubTileGraphics, context: ComponentRenderContext<DefaultRadioButton>) {
        val style = context.componentStyle.currentStyle()
        tileGraphics.applyStyle(style)
        val checkBoxState = context.component.state
        val text = context.component.text
        val maxTextLength = max(0, tileGraphics.size.width - BUTTON_WIDTH - 1)
        val clearedText = if (text.length > maxTextLength) {
            text.substring(0, maxTextLength - 3).plus(ELLIPSIS)
        } else {
            text
        }
        tileGraphics.putText("${STATES[checkBoxState]!!} $clearedText")
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
