package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.component.CheckBox
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.tileset.Tileset
import org.hexworks.zircon.internal.component.impl.DefaultCheckBox
import org.hexworks.zircon.internal.config.RuntimeConfig
import org.hexworks.zircon.platform.factory.ThreadSafeQueueFactory

data class CheckBoxBuilder(
        private var tileset: TilesetResource = RuntimeConfig.config.defaultTileset,
        private var text: String = "",
        private var position: Position = Position.defaultPosition(),
        private var componentStyleSet: ComponentStyleSet = ComponentStyleSet.defaultStyleSet(),
        private var width: Int = -1) : Builder<CheckBox> {

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
                initialTileset = tileset)
    }

    override fun createCopy() = copy()

    companion object {

        fun newBuilder() = CheckBoxBuilder()
    }
}
