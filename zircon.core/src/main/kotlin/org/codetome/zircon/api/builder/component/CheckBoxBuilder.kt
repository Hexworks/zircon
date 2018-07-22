package org.codetome.zircon.api.builder.component

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.builder.Builder
import org.codetome.zircon.api.component.CheckBox
import org.codetome.zircon.api.component.ComponentStyleSet
import org.codetome.zircon.api.font.Font
import org.codetome.zircon.api.graphics.Layer
import org.codetome.zircon.internal.component.impl.DefaultCheckBox
import org.codetome.zircon.internal.font.impl.FontSettings
import org.codetome.zircon.platform.factory.ThreadSafeQueueFactory

data class CheckBoxBuilder(
        private var font: Font = FontSettings.NO_FONT,
        private var text: String = "",
        private var position: Position = Position.defaultPosition(),
        private var componentStyleSet: ComponentStyleSet = ComponentStyleSet.defaultStyleSet(),
        private var width: Int = -1) : Builder<CheckBox> {

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

    fun width(width: Int) = also {
        this.width = width
    }

    override fun build(): CheckBox {
        return DefaultCheckBox(
                text = text,
                width = if (width == -1) text.length + 4 else width,
                position = position,
                componentStyleSet = componentStyleSet,
                wrappers = ThreadSafeQueueFactory.create(),
                initialFont = font)
    }

    override fun createCopy() = copy()

    companion object {

        fun newBuilder() = CheckBoxBuilder()
    }
}
