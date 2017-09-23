package org.codetome.zircon.api.component.builder

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.builder.Builder
import org.codetome.zircon.api.builder.ComponentStylesBuilder
import org.codetome.zircon.api.component.ComponentStyles
import org.codetome.zircon.api.component.RadioButtonGroup
import org.codetome.zircon.internal.component.WrappingStrategy
import org.codetome.zircon.internal.component.impl.DefaultRadioButtonGroup
import java.util.*

data class RadioButtonGroupBuilder(
        private var position: Position = Position.DEFAULT_POSITION,
        private var componentStyles: ComponentStyles = ComponentStylesBuilder.DEFAULT,
        private var size: Size = Size.ONE) : Builder<RadioButtonGroup> {

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
                wrappers = LinkedList<WrappingStrategy>(),
                size = size,
                position = position,
                componentStyles = componentStyles)
    }

    override fun createCopy() = copy()

    companion object {

        @JvmStatic
        fun newBuilder() = RadioButtonGroupBuilder()
    }
}