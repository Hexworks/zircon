package org.codetome.zircon.api.component.builder

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.builder.Builder
import org.codetome.zircon.api.builder.ComponentStylesBuilder
import org.codetome.zircon.api.component.ComponentStyles
import org.codetome.zircon.api.component.TextBox
import org.codetome.zircon.internal.component.impl.DefaultTextBox

class TextBoxBuilder : Builder<TextBox> {

    private var text = ""
    private var position = Position.DEFAULT_POSITION
    private var size = Size.ONE
    private var componentStyles: ComponentStyles = ComponentStylesBuilder.DEFAULT

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
                componentStyles = componentStyles)
    }

    companion object {

        @JvmStatic
        fun newBuilder() = TextBoxBuilder()
    }
}