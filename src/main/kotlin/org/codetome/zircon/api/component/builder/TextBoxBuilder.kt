package org.codetome.zircon.api.component.builder

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.builder.Builder
import org.codetome.zircon.api.builder.ComponentStylesBuilder
import org.codetome.zircon.api.component.ComponentStyles
import org.codetome.zircon.api.component.TextBox
import org.codetome.zircon.api.font.Font
import org.codetome.zircon.api.graphics.Layer
import org.codetome.zircon.internal.component.impl.DefaultTextBox
import org.codetome.zircon.internal.font.impl.FontSettings
import java.awt.image.BufferedImage

data class TextBoxBuilder(
        private var font: Font<BufferedImage> = FontSettings.NO_FONT,
        private var text: String = "",
        private var position: Position = Position.DEFAULT_POSITION,
        private var size: Size = Size.ONE,
        private var componentStyles: ComponentStyles = ComponentStylesBuilder.DEFAULT) : Builder<TextBox> {

    /**
     * Sets the [Font] to use with the resulting [Layer].
     */
    fun font(font: Font<BufferedImage>) = also {
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

    fun componentStyles(componentStyles: ComponentStyles) = also {
        this.componentStyles = componentStyles
    }

    override fun build(): TextBox {
        return DefaultTextBox(
                text = text,
                initialSize = size,
                position = position,
                componentStyles = componentStyles,
                initialFont = font)
    }

    override fun createCopy() = copy()

    companion object {

        @JvmStatic
        fun newBuilder() = TextBoxBuilder()
    }
}