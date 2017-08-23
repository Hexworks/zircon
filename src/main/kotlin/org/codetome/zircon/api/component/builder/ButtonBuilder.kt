package org.codetome.zircon.api.component.builder

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.builder.Builder
import org.codetome.zircon.api.builder.ComponentStylesBuilder
import org.codetome.zircon.api.component.Button
import org.codetome.zircon.api.component.ComponentStyles
import org.codetome.zircon.api.component.Label
import org.codetome.zircon.internal.component.impl.DefaultButton
import org.codetome.zircon.internal.component.impl.DefaultLabel

class ButtonBuilder : Builder<Button> {

    private var text = ""
    private var position = Position.DEFAULT_POSITION
    private var componentStyles: ComponentStyles = ComponentStylesBuilder.DEFAULT

    fun text(text: String) = also {
        this.text = text
    }

    fun position(position: Position) = also {
        this.position = position
    }

    fun componentStyles(componentStyles: ComponentStyles) = also {
        this.componentStyles = componentStyles
    }

    override fun build(): Button {
        require(text.isNotBlank()) {
            "A Label can't be blank!"
        }
        return DefaultButton(
                text = text,
                initialSize = Size.of(text.length + 2, 1),
                position = position,
                componentStyles = componentStyles
        )
    }

    companion object {

        @JvmStatic
        fun newBuilder() = ButtonBuilder()
    }
}