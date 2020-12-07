package org.hexworks.zircon.internal.component.renderer

import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.internal.component.impl.DefaultCheckBox
import org.hexworks.zircon.internal.component.impl.DefaultCheckBox.CheckBoxState.CHECKED
import org.hexworks.zircon.internal.component.impl.DefaultCheckBox.CheckBoxState.CHECKING
import org.hexworks.zircon.internal.component.impl.DefaultCheckBox.CheckBoxState.UNCHECKED
import org.hexworks.zircon.internal.component.impl.DefaultCheckBox.CheckBoxState.UNCHECKING

@Suppress("DuplicatedCode")
class DefaultCheckBoxRenderer : ComponentRenderer<DefaultCheckBox> {

    override fun render(tileGraphics: TileGraphics, context: ComponentRenderContext<DefaultCheckBox>) {
        val state = context.component.checkBoxState
        val text = context.component.text
        val labelAlignment = context.component.labelAlignment

        val checkBox = STATES.getValue(state)
        val checkBoxWithLabel =
            if (text == "") checkBox
            else {
                val maxTextLength = tileGraphics.size.width - DECORATION_WIDTH
                val clearedText =
                    if (text.length > maxTextLength) {
                        text.substring(0, maxTextLength - 3).plus(ELLIPSIS)
                    } else {
                        text
                    }
                when (labelAlignment) {
                    DefaultCheckBox.CheckBoxAlignment.LEFT -> "$clearedText $checkBox"
                    DefaultCheckBox.CheckBoxAlignment.RIGHT -> "$checkBox $clearedText"
                }
            }

        tileGraphics.fillWithText(
            text = checkBoxWithLabel,
            style = context.currentStyle
        )
    }

    companion object {
        private const val ELLIPSIS = "..."
        private const val CHECKING_BUTTON = "[+]"
        private const val UNCHECKING_BUTTON = "[-]"
        private const val CHECKED_BUTTON = "[*]"
        private const val UNCHECKED_BUTTON = "[ ]"

        const val BUTTON_WIDTH = CHECKING_BUTTON.length
        const val DECORATION_WIDTH = BUTTON_WIDTH + 1

        private val STATES = mapOf(
            Pair(UNCHECKED, UNCHECKED_BUTTON),
            Pair(CHECKING, CHECKING_BUTTON),
            Pair(CHECKED, CHECKED_BUTTON),
            Pair(UNCHECKING, UNCHECKING_BUTTON)
        )

    }
}
