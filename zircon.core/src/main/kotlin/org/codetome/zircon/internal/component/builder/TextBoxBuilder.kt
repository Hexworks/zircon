package org.codetome.zircon.internal.component.builder

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.builder.Builder
import org.codetome.zircon.api.builder.ComponentStyleSetBuilder
import org.codetome.zircon.api.component.ComponentStyleSet
import org.codetome.zircon.api.component.TextBox
import org.codetome.zircon.api.font.Font
import org.codetome.zircon.api.graphics.Layer
import org.codetome.zircon.internal.component.impl.DefaultTextBox
import org.codetome.zircon.internal.font.impl.FontSettings

data class TextBoxBuilder(
        private var font: Font = FontSettings.NO_FONT,
        private var text: String = "",
        private var position: Position = Position.defaultPosition(),
        private var size: Size = Size.one(),
        private var componentStyleSet: ComponentStyleSet = ComponentStyleSetBuilder.DEFAULT) : Builder<TextBox> {

    /**
     * Sets the [Font] to use with the resulting [Layer].
     */
    fun font(font: Font) = also {
        this.font = font
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
                initialFont = font)
    }

    override fun createCopy() = copy()

    companion object {

        fun newBuilder() = TextBoxBuilder()
    }
}
