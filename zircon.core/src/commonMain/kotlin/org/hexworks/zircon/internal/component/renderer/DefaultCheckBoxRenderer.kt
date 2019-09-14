package org.hexworks.zircon.internal.component.renderer

import org.hexworks.zircon.api.Positions
import org.hexworks.zircon.api.Tiles
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.graphics.impl.SubTileGraphics
import org.hexworks.zircon.internal.component.impl.DefaultCheckBox
import org.hexworks.zircon.internal.component.impl.DefaultCheckBox.CheckBoxState.*

class DefaultCheckBoxRenderer : ComponentRenderer<DefaultCheckBox> {

    override fun render(tileGraphics: SubTileGraphics, context: ComponentRenderContext<DefaultCheckBox>) {
        val style = context.componentStyle.currentStyle()
        tileGraphics.applyStyle(style)
        val checkBoxState = context.component.state
        val text = context.component.text
        val maxTextLength = tileGraphics.size.width - BUTTON_WIDTH - 1
        val clearedText = if (text.length > maxTextLength) {
            text.substring(0, maxTextLength - 3).plus("...")
        } else {
            text
        }
        val finalText = "${STATES.getValue(checkBoxState)} $clearedText"
        tileGraphics.putText(finalText)
        (finalText.length until tileGraphics.width).forEach { idx ->
            tileGraphics.setTileAt(Positions.create(idx, 0), Tiles.empty())
        }
    }

    companion object {

        private const val CHECKING_BUTTON = "[+]"
        private const val UNCHECKING_BUTTON = "[-]"
        private const val CHECKED_BUTTON = "[*]"
        private const val UNCHECKED_BUTTON = "[ ]"
        private const val BUTTON_WIDTH = CHECKING_BUTTON.length

        const val DECORATION_WIDTH = BUTTON_WIDTH + 1

        private val STATES = mapOf(
                Pair(UNCHECKED, UNCHECKED_BUTTON),
                Pair(CHECKING, CHECKING_BUTTON),
                Pair(CHECKED, CHECKED_BUTTON),
                Pair(UNCHECKING, UNCHECKING_BUTTON))

    }
}
