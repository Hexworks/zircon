package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.Panel
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.tileset.Tileset
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.api.modifier.Border
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.component.WrappingStrategy
import org.hexworks.zircon.internal.component.impl.DefaultPanel
import org.hexworks.zircon.internal.component.impl.wrapping.BorderWrappingStrategy
import org.hexworks.zircon.internal.component.impl.wrapping.BoxWrappingStrategy
import org.hexworks.zircon.internal.component.impl.wrapping.ShadowWrappingStrategy
import org.hexworks.zircon.api.util.Maybe
import org.hexworks.zircon.internal.config.RuntimeConfig

data class PanelBuilder(private var tileset: TilesetResource = RuntimeConfig.config.defaultTileset,
                        private var boxType: BoxType = BoxType.SINGLE,
                        private var title: String = "",
                        private var position: Position = Position.defaultPosition(),
                        private var componentStyleSet: ComponentStyleSet = ComponentStyleSet.defaultStyleSet(),
                        private var size: Size = Size.unknown(),
                        private var drawBox: Boolean = false,
                        private var drawShadow: Boolean = false,
                        private var border: Maybe<Border> = Maybe.empty()) : Builder<Panel> {

    /**
     * Sets the [Tileset] to use with the resulting [Layer].
     */
    fun tileset(tileset: TilesetResource) = also {
        this.tileset = tileset
    }

    fun wrapWithBox() = also {
        drawBox = true
    }

    fun wrapWithShadow() = also {
        drawShadow = true
    }

    fun addBorder(border: Border) = also {
        this.border = Maybe.of(border)
    }

    fun boxType(boxType: BoxType) = also {
        this.boxType = boxType
    }

    fun size(size: Size) = also {
        this.size = size
    }

    fun title(title: String) = also {
        this.title = title
    }

    fun position(position: Position) = also {
        this.position = position
    }

    fun componentStyles(componentStyleSet: ComponentStyleSet) = also {
        this.componentStyleSet = componentStyleSet
    }

    override fun build(): Panel {
        require(size != Size.unknown()) {
            "You must set a size for a Panel!"
        }
        val wrappers = mutableListOf<WrappingStrategy>()
        if (drawBox) {
            wrappers.add(BoxWrappingStrategy(
                    boxType = boxType,
                    title = if (title.isNotBlank()) Maybe.of(title) else Maybe.empty()))
        }
        if (border.isPresent) {
            wrappers.add(BorderWrappingStrategy(border.get()))
        }
        if (drawShadow) {
            wrappers.add(ShadowWrappingStrategy())
        }
        return DefaultPanel(
                title = title,
                initialSize = size,
                position = position,
                componentStyleSet = componentStyleSet,
                wrappers = wrappers,
                initialTileset = tileset)
    }

    override fun createCopy() = this.copy()

    companion object {

        fun newBuilder() = PanelBuilder()
    }
}
