package org.hexworks.zircon.internal.component.renderer

import org.hexworks.zircon.api.component.BinarySelector
import org.hexworks.zircon.api.component.BinarySelector.LabelAlignment.LEFT
import org.hexworks.zircon.api.component.BinarySelector.LabelAlignment.RIGHT
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.graphics.DrawWindow

/**
 * `[ ]` / `[*]`, and `[+]` / `[-]` while the press is pending, next to the label.
 */
public class DefaultCheckBoxRenderer : ComponentRenderer<BinarySelector> {

    override fun render(drawWindow: DrawWindow, context: ComponentRenderContext<BinarySelector>) {
        val component = context.component
        val text = component.text
        val checkBox = markFor(component)
        val checkBoxWithLabel =
            if (text == "") checkBox
            else {
                val maxTextLength = drawWindow.size.width - DECORATION_WIDTH
                val clearedText =
                    if (text.length > maxTextLength) {
                        text.substring(0, maxTextLength - 3).plus(ELLIPSIS)
                    } else {
                        text
                    }
                when (component.labelAlignment) {
                    LEFT -> "$clearedText $checkBox"
                    RIGHT -> "$checkBox $clearedText"
                }
            }
        drawWindow.fillWithText(
            text = checkBoxWithLabel,
            style = context.currentStyle
        )
    }

    public companion object {
        private const val ELLIPSIS = "..."
        private const val CHECKING_BUTTON = "[+]"
        private const val UNCHECKING_BUTTON = "[-]"
        private const val CHECKED_BUTTON = "[*]"
        private const val UNCHECKED_BUTTON = "[ ]"
        public const val BUTTON_WIDTH: Int = CHECKING_BUTTON.length
        public const val DECORATION_WIDTH: Int = BUTTON_WIDTH + 1

        internal fun markFor(selector: BinarySelector): String = when {
            selector.isPressed && selector.isSelected -> UNCHECKING_BUTTON
            selector.isPressed -> CHECKING_BUTTON
            selector.isSelected -> CHECKED_BUTTON
            else -> UNCHECKED_BUTTON
        }
    }
}
