package org.codetome.zircon.api.component.builder

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.builder.Builder
import org.codetome.zircon.api.builder.ComponentStylesBuilder
import org.codetome.zircon.api.component.ComponentStyles
import org.codetome.zircon.api.component.Label
import org.codetome.zircon.api.font.Font
import org.codetome.zircon.api.graphics.Layer
import org.codetome.zircon.internal.component.impl.DefaultLabel
import org.codetome.zircon.internal.font.impl.FontSettings
import java.awt.image.BufferedImage

data class LabelBuilder(
        private var font: Font = FontSettings.NO_FONT,
        private var text: String = "",
        private var position: Position = Position.defaultPosition(),
        private var componentStyles: ComponentStyles = ComponentStylesBuilder.DEFAULT) : Builder<Label> {

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

    override fun build(): Label {
        require(text.isNotBlank()) {
            "A Label can't be blank!"
        }
        return DefaultLabel(
                text = text,
                initialSize = Size.create(text.length, 1),
                initialFont = font,
                position = position,
                componentStyles = componentStyles
        )
    }

    override fun createCopy() = copy()

    companion object {

        @JvmStatic
        fun newBuilder() = LabelBuilder()
    }
}
