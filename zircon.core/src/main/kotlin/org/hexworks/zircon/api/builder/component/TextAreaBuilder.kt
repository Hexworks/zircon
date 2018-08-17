package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.TextArea
import org.hexworks.zircon.api.tileset.Tileset
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.component.impl.DefaultTextArea
import org.hexworks.zircon.internal.config.RuntimeConfig

data class TextAreaBuilder(
        private var tileset: TilesetResource = RuntimeConfig.config.defaultTileset,
        private var text: String = "",
        private var position: Position = Position.defaultPosition(),
        private var size: Size = Size.one(),
        private var componentStyleSet: ComponentStyleSet = ComponentStyleSet.defaultStyleSet()) : Builder<TextArea> {

    /**
     * Sets the [Tileset] to use with the resulting [Layer].
     */
    fun tileset(tileset: TilesetResource) = also {
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

    override fun build(): TextArea {
        return DefaultTextArea(
                text = text,
                initialSize = size,
                position = position,
                componentStyleSet = componentStyleSet,
                initialTileset = tileset)
    }

    override fun createCopy() = copy()

    companion object {

        fun newBuilder() = TextAreaBuilder()
    }
}
