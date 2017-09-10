package org.codetome.zircon.api.component.builder

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.builder.Builder
import org.codetome.zircon.api.builder.ComponentStylesBuilder
import org.codetome.zircon.api.component.CheckBox
import org.codetome.zircon.api.component.ComponentStyles
import org.codetome.zircon.internal.component.WrappingStrategy
import org.codetome.zircon.internal.component.impl.DefaultCheckBox
import java.util.*

data class CheckBoxBuilder(
        private var text: String = "",
        private var position: Position = Position.DEFAULT_POSITION,
        private var componentStyles: ComponentStyles = ComponentStylesBuilder.DEFAULT,
        private var width: Int = -1) : Builder<CheckBox> {

    fun text(text: String) = also {
        this.text = text
    }

    fun position(position: Position) = also {
        this.position = position
    }

    fun componentStyles(componentStyles: ComponentStyles) = also {
        this.componentStyles = componentStyles
    }

    fun width(width: Int) = also {
        this.width = width
    }

    override fun build(): CheckBox {
        return DefaultCheckBox(
                text = text,
                width = if (width == -1) text.length + 4 else width,
                position = position,
                componentStyles = componentStyles,
                wrappers = LinkedList<WrappingStrategy>())
    }

    override fun createCopy() = copy()

    companion object {

        @JvmStatic
        fun newBuilder() = CheckBoxBuilder()
    }
}