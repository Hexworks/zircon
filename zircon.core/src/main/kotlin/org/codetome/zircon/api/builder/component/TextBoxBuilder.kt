package org.codetome.zircon.api.builder.component

import org.codetome.zircon.api.data.Position
import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.builder.Builder
import org.codetome.zircon.api.component.ComponentStyleSet
import org.codetome.zircon.api.component.TextBox
import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.tileset.Tileset
import org.codetome.zircon.api.graphics.Layer
import org.codetome.zircon.api.resource.TilesetResource
import org.codetome.zircon.internal.component.impl.DefaultTextBox
import org.codetome.zircon.internal.config.RuntimeConfig

data class TextBoxBuilder(
        private var tileset: TilesetResource<out Tile> = RuntimeConfig.config.defaultTileset,
        private var text: String = "",
        private var position: Position = Position.defaultPosition(),
        private var size: Size = Size.one(),
        private var componentStyleSet: ComponentStyleSet = ComponentStyleSet.defaultStyleSet()) : Builder<TextBox> {

    /**
     * Sets the [Tileset] to use with the resulting [Layer].
     */
    fun font(tileset: TilesetResource<out Tile>) = also {
        this.tileset = tileset
    }

    fun text(text: String) = also {
        this.text = text
    }

    fun position(position: Position) = also {
        this.position = position
    }

    fun size(size: Size) = also {
        this.size = size
    }

    fun componentStyles(componentStyleSet: ComponentStyleSet) = also {
        this.componentStyleSet = componentStyleSet
    }

    override fun build(): TextBox {
        return DefaultTextBox(
                text = text,
                initialSize = size,
                position = position,
                componentStyleSet = componentStyleSet,
                initialTileset = tileset)
    }

    override fun createCopy() = copy()

    companion object {

        fun newBuilder() = TextBoxBuilder()
    }
}
