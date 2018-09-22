package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.BaseComponentBuilder
import org.hexworks.zircon.api.component.CommonComponentProperties
import org.hexworks.zircon.api.component.TextBox
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.internal.component.impl.DefaultTextBox
import org.hexworks.zircon.platform.util.SystemUtils

data class TextBoxBuilder(
        private var text: String = "",
        private var size: Size = Size.one(),
        private val commonComponentProperties: CommonComponentProperties = CommonComponentProperties())
    : BaseComponentBuilder<TextBox, TextBoxBuilder>(commonComponentProperties) {

    fun paragraph(paragraph: String) = also {
        paragraph(paragraph, true)
    }

    fun paragraph(paragraph: String, withNewLine: Boolean = true) = also {
        paragraph.forEachIndexed { idx, char ->
            val needsNewLine = idx.rem(size.width()) == 0 && idx != 0
            if (needsNewLine) {
                this.text += SystemUtils.getLineSeparator()
            }
            if ((needsNewLine && char == ' ').not()) {
                this.text += char
            }
        }
        if (withNewLine) newLine()
    }

    fun listItem(item: String) = also {
        this.text += "- "
        item.forEachIndexed { idx, char ->
            val needsNewLine = idx.rem(size.width()) == 0 && idx != 0
            if (needsNewLine) {
                this.text += SystemUtils.getLineSeparator() + "  "
            }
            if ((needsNewLine && char == ' ').not()) {
                this.text += "$char"
            }
        }
        newLine()
    }

    fun newLine() = also {
        this.text += SystemUtils.getLineSeparator()
    }

    fun text(text: String) = also {
        this.text = text
    }


    override fun build(): TextBox {
        return DefaultTextBox(
                text = text,
                initialSize = size,
                position = position(),
                componentStyleSet = componentStyleSet(),
                initialTileset = tileset())
    }

    override fun createCopy() = copy()

    companion object {

        fun newBuilder() = TextBoxBuilder()
    }
}
