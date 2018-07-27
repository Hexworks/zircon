package org.codetome.zircon.api.builder.component

import org.codetome.zircon.api.data.Position
import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.builder.Builder
import org.codetome.zircon.api.component.Button
import org.codetome.zircon.api.component.ComponentStyleSet
import org.codetome.zircon.api.tileset.Tileset
import org.codetome.zircon.api.graphics.Layer
import org.codetome.zircon.internal.component.WrappingStrategy
import org.codetome.zircon.internal.component.impl.DefaultButton
import org.codetome.zircon.internal.component.impl.wrapping.ButtonWrappingStrategy
import org.codetome.zircon.internal.tileset.impl.FontSettings
import org.codetome.zircon.platform.factory.ThreadSafeQueueFactory

data class ButtonBuilder(
        private var tileset: Tileset = FontSettings.NO_FONT,
        private var text: String = "",
        private var position: Position = Position.defaultPosition(),
        private var componentStyleSet: ComponentStyleSet = ComponentStyleSet.defaultStyleSet()) : Builder<Button> {

    /**
     * Sets the [Tileset] to use with the resulting [Layer].
     */
    fun font(tileset: Tileset) = also {
        this.tileset = tileset
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

    override fun build(): Button {
        require(text.isNotBlank()) {
            "A Button can't be blank!"
        }
        val wrappers = ThreadSafeQueueFactory.create<WrappingStrategy>()
        wrappers.add(ButtonWrappingStrategy())
        return DefaultButton(
                text = text,
                initialSize = Size.create(text.length + 2, 1),
                position = position,
                componentStyleSet = componentStyleSet,
                wrappers = wrappers,
                initialTileset = tileset)
    }

    override fun createCopy() = copy()

    companion object {

        fun newBuilder() = ButtonBuilder()
    }
}
