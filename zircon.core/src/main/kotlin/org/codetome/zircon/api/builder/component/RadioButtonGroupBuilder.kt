package org.codetome.zircon.api.builder.component

import org.codetome.zircon.api.data.Position
import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.builder.Builder
import org.codetome.zircon.api.component.ComponentStyleSet
import org.codetome.zircon.api.component.RadioButtonGroup
import org.codetome.zircon.api.tileset.Tileset
import org.codetome.zircon.api.graphics.Layer
import org.codetome.zircon.internal.component.impl.DefaultRadioButtonGroup
import org.codetome.zircon.internal.tileset.impl.FontSettings
import org.codetome.zircon.platform.factory.ThreadSafeQueueFactory

data class RadioButtonGroupBuilder(
        private var tileset: Tileset = FontSettings.NO_FONT,
        private var position: Position = Position.defaultPosition(),
        private var componentStyleSet: ComponentStyleSet = ComponentStyleSet.defaultStyleSet(),
        private var size: Size = Size.one()) : Builder<RadioButtonGroup> {

    /**
     * Sets the [Tileset] to use with the resulting [Layer].
     */
    fun font(tileset: Tileset) = also {
        this.tileset = tileset
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
                initialTileset = tileset)
    }

    override fun createCopy() = copy()

    companion object {

        fun newBuilder() = RadioButtonGroupBuilder()
    }
}
