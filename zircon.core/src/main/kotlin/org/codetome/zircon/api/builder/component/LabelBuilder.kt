package org.codetome.zircon.api.builder.component

import org.codetome.zircon.api.data.Position
import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.builder.Builder
import org.codetome.zircon.api.component.ComponentStyleSet
import org.codetome.zircon.api.component.Label
import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.tileset.Tileset
import org.codetome.zircon.api.graphics.Layer
import org.codetome.zircon.api.resource.TilesetResource
import org.codetome.zircon.internal.component.impl.DefaultLabel
import org.codetome.zircon.internal.config.RuntimeConfig

data class LabelBuilder(
        private var tileset: TilesetResource<out Tile> = RuntimeConfig.config.defaultTileset,
        private var text: String = "",
        private var position: Position = Position.defaultPosition(),
        private var componentStyleSet: ComponentStyleSet = ComponentStyleSet.defaultStyleSet()) : Builder<Label> {

    /**
     * Sets the [Tileset] to use with the resulting [Layer].
     */
    fun tileset(tileset: TilesetResource<out Tile>) = also {
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
