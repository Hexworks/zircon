package org.codetome.zircon.api.component.builder

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.builder.Builder
import org.codetome.zircon.api.builder.ComponentStylesBuilder
import org.codetome.zircon.api.component.ComponentStyles
import org.codetome.zircon.api.component.Label
import org.codetome.zircon.internal.component.impl.DefaultLabel

class LabelBuilder : Builder<Label> {

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

    override fun build(): Label {
        require(text.isNotBlank()) {
            "A Label can't be blank!"
        }
        return DefaultLabel(
                text = text,
                initialSize = Size.of(text.length, 1),
                position = position,
                componentStyles = componentStyles
        )
    }

    companion object {

        @JvmStatic
        fun newBuilder() = LabelBuilder()
    }
}