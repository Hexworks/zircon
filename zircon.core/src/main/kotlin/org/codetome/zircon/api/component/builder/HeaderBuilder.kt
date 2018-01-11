package org.codetome.zircon.api.component.builder

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.builder.Builder
import org.codetome.zircon.api.builder.ComponentStylesBuilder
import org.codetome.zircon.api.component.ComponentStyles
import org.codetome.zircon.api.component.Header
import org.codetome.zircon.api.font.Font
import org.codetome.zircon.api.graphics.Layer
import org.codetome.zircon.internal.component.impl.DefaultHeader
import org.codetome.zircon.internal.font.impl.FontSettings
import java.awt.image.BufferedImage

data class HeaderBuilder(
        private var font: Font = FontSettings.NO_FONT,
        private var text: String = "",
        private var position: Position = Position.DEFAULT_POSITION,
        private var componentStyles: ComponentStyles = ComponentStylesBuilder.DEFAULT) : Builder<Header> {

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

    fun componentStyles(componentStyles: ComponentStyles) = also {
        this.componentStyles = componentStyles
    }

    override fun build(): Header {
        require(text.isNotBlank()) {
            "A Header can't be blank!"
        }
        return DefaultHeader(
                text = text,
                initialSize = Size.of(text.length, 1),
                position = position,
                componentStyles = componentStyles,
                initialFont = font)
    }

    override fun createCopy() = copy()

    companion object {

        @JvmStatic
        fun newBuilder() = HeaderBuilder()
    }
}