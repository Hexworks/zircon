package org.codetome.zircon.internal.component.builder

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.builder.Builder
import org.codetome.zircon.api.builder.ComponentStyleSetBuilder
import org.codetome.zircon.api.component.ComponentStyleSet
import org.codetome.zircon.api.component.RadioButtonGroup
import org.codetome.zircon.api.font.Font
import org.codetome.zircon.api.graphics.Layer
import org.codetome.zircon.internal.component.impl.DefaultRadioButtonGroup
import org.codetome.zircon.internal.font.impl.FontSettings
import org.codetome.zircon.platform.factory.ThreadSafeQueueFactory

data class RadioButtonGroupBuilder(
        private var font: Font = FontSettings.NO_FONT,
        private var position: Position = Position.defaultPosition(),
        private var componentStyleSet: ComponentStyleSet = ComponentStyleSetBuilder.DEFAULT,
        private var size: Size = Size.one()) : Builder<RadioButtonGroup> {

    /**
     * Sets the [Font] to use with the resulting [Layer].
     */
    fun font(font: Font) = also {
        this.font = font
    }

    fun position(position: Position) = also {
        this.position = position
    }

    fun componentStyles(componentStyleSet: ComponentStyleSet) = also {
        this.componentStyleSet = componentStyleSet
    }

    fun size(size: Size) = also {
        this.size = size
    }

    override fun build(): RadioButtonGroup {
        return DefaultRadioButtonGroup(
                wrappers = ThreadSafeQueueFactory.create(),
                size = size,
                position = position,
                componentStyleSet = componentStyleSet,
                initialFont = font)
    }

    override fun createCopy() = copy()

    companion object {

        fun newBuilder() = RadioButtonGroupBuilder()
    }
}
