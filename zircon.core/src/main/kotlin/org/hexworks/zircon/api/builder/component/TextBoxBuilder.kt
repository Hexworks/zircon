package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.TextBox
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.tileset.Tileset
import org.hexworks.zircon.internal.component.impl.DefaultTextBox
import org.hexworks.zircon.internal.config.RuntimeConfig
import org.hexworks.zircon.platform.util.SystemUtils

data class TextBoxBuilder(
        private var tileset: TilesetResource = RuntimeConfig.config.defaultTileset,
        private var text: String = "",
        private var position: Position = Position.defaultPosition(),
        private var size: Size = Size.one(),
        private var componentStyleSet: ComponentStyleSet = ComponentStyleSet.defaultStyleSet()) : Builder<TextBox> {

    /**
     * Sets the [Tileset] to use with the resulting [Layer].
     */
    fun tileset(tileset: TilesetResource) = also {
        this.tileset = tileset
    }

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
        if(withNewLine) newLine()
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

    fun position(position: Position) = also {
        this.position = position
    }

    fun size(size: Size) = also {
        this.size = size
    }

    fun componentStyles(componentStyleSet: ComponentStyleSet) = also {
        this.componentStyleSet = componentStyleSet
    }

    override fun build(): TextBox {
        return DefaultTextBox(
                text = text,
                initialSize = size,
                position = position,
                componentStyleSet = componentStyleSet,
                initialTileset = tileset)
    }

    override fun createCopy() = copy()

    companion object {

        fun newBuilder() = TextBoxBuilder()
    }
}
