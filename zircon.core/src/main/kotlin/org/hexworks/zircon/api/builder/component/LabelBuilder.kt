package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.Label
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.tileset.Tileset
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.component.impl.DefaultLabel
import org.hexworks.zircon.internal.config.RuntimeConfig

data class LabelBuilder(
        private var tileset: TilesetResource = RuntimeConfig.config.defaultTileset,
        private var text: String = "",
        private var position: Position = Position.defaultPosition(),
        private var componentStyleSet: ComponentStyleSet = ComponentStyleSet.defaultStyleSet()) : Builder<Label> {

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
                initialTileset = tileset,
                position = position,
                componentStyleSet = componentStyleSet
        )
    }

    override fun createCopy() = copy()

    companion object {

        fun newBuilder() = LabelBuilder()
    }
}
