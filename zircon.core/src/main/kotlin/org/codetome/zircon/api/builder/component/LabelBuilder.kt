package org.codetome.zircon.api.builder.component

import org.codetome.zircon.api.data.Position
import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.builder.Builder
import org.codetome.zircon.api.component.ComponentStyleSet
import org.codetome.zircon.api.component.Label
import org.codetome.zircon.api.font.Font
import org.codetome.zircon.api.graphics.Layer
import org.codetome.zircon.internal.component.impl.DefaultLabel
import org.codetome.zircon.internal.font.impl.FontSettings

data class LabelBuilder(
        private var font: Font = FontSettings.NO_FONT,
        private var text: String = "",
        private var position: Position = Position.defaultPosition(),
        private var componentStyleSet: ComponentStyleSet = ComponentStyleSet.defaultStyleSet()) : Builder<Label> {

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

    fun componentStyles(componentStyleSet: ComponentStyleSet) = also {
        this.componentStyleSet = componentStyleSet
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
                componentStyleSet = componentStyleSet
        )
    }

    override fun createCopy() = copy()

    companion object {

        fun newBuilder() = LabelBuilder()
    }
}
