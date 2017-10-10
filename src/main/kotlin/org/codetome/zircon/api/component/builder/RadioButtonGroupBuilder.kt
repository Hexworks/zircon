package org.codetome.zircon.api.component.builder

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.builder.Builder
import org.codetome.zircon.api.builder.ComponentStylesBuilder
import org.codetome.zircon.api.component.ComponentStyles
import org.codetome.zircon.api.component.RadioButtonGroup
import org.codetome.zircon.api.font.Font
import org.codetome.zircon.api.graphics.Layer
import org.codetome.zircon.internal.component.WrappingStrategy
import org.codetome.zircon.internal.component.impl.DefaultRadioButtonGroup
import org.codetome.zircon.internal.font.impl.FontSettings
import java.awt.image.BufferedImage
import java.util.*

data class RadioButtonGroupBuilder(
        private var font: Font<BufferedImage> = FontSettings.NO_FONT,
        private var position: Position = Position.DEFAULT_POSITION,
        private var componentStyles: ComponentStyles = ComponentStylesBuilder.DEFAULT,
        private var size: Size = Size.ONE,
        private var spacing: Int = 1) : Builder<RadioButtonGroup> {

    /**
     * Sets the [Font] to use with the resulting [Layer].
     */
    fun font(font: Font<BufferedImage>) = also {
        this.font = font
    }

    fun spacing(spacing: Int) = also {
        this.spacing = spacing
    }

    fun position(position: Position) = also {
        this.position = position
    }

    fun componentStyles(componentStyles: ComponentStyles) = also {
        this.componentStyles = componentStyles
    }

    fun size(size: Size) = also {
        this.size = size
    }

    override fun build(): RadioButtonGroup {
        return DefaultRadioButtonGroup(
                spacing = spacing,
                wrappers = LinkedList<WrappingStrategy>(),
                size = size,
                position = position,
                componentStyles = componentStyles,
                initialFont = font)
    }

    override fun createCopy() = copy()

    companion object {

        @JvmStatic
        fun newBuilder() = RadioButtonGroupBuilder()
    }
}