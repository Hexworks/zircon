package org.hexworks.zircon.internal.component.renderer

import org.hexworks.zircon.api.component.BinarySelector
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.graphics.DrawWindow
import kotlin.math.max

/**
 * `< >` / `<O>`, and `<o>` while the press is pending, before the label.
 */
public class DefaultRadioButtonRenderer : ComponentRenderer<BinarySelector> {

    override fun render(drawWindow: DrawWindow, context: ComponentRenderContext<BinarySelector>) {
        val component = context.component
        val text = component.text
        val maxTextLength = max(0, drawWindow.size.width - DECORATION_WIDTH)
        val clearedText = if (text.length > maxTextLength) {
            text.substring(0, max(0, maxTextLength - 3)).plus(ELLIPSIS)
        } else {
            text
        }
        drawWindow.fillWithText(
            text = "${markFor(component)} $clearedText",
            style = context.currentStyle
        )
    }

    public companion object {
        private const val ELLIPSIS = "..."
        private const val PRESSED_BUTTON = "<o>"
        private const val SELECTED_BUTTON = "<O>"
        private const val NOT_SELECTED_BUTTON = "< >"
        public const val DECORATION_WIDTH: Int = NOT_SELECTED_BUTTON.length + 1

        internal fun markFor(selector: BinarySelector): String = when {
            selector.isPressed -> PRESSED_BUTTON
            selector.isSelected -> SELECTED_BUTTON
            else -> NOT_SELECTED_BUTTON
        }
    }
}
