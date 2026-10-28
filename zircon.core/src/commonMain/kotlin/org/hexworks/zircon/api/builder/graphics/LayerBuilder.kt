package org.hexworks.zircon.api.builder.graphics

import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.builder.data.CharacterTileBuilder
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.extensions.isSizeNotUnknown
import org.hexworks.zircon.api.dsl.ZirconDsl
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.api.graphics.extensions.fill
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.config.RuntimeConfig
import org.hexworks.zircon.internal.graphics.DefaultLayer

/**
 * Use this to build [Layer]s. Defaults are:
 * - size: [Size.ONE]
 * - filler: [Tile.empty()]
 * - offset: [Position.ZERO]
 * - has no text image by default
 */
@ZirconDsl
public class LayerBuilder : Builder<Layer> {

    public var tileset: TilesetResource = RuntimeConfig.config.defaultTileset
    public var size: Size = Size.UNKNOWN
    public var offset: Position = Position.ZERO
    public var tileGraphics: TileGraphics? = null
    public var filler: Tile = Tile.empty()

    override fun build(): Layer {
        return tileGraphics?.let { graphics ->
            DefaultLayer(
                initialPosition = offset,
                initialContents = graphics
            )
        } ?: run {
            require(size.isSizeNotUnknown) {
                "A Layer must has a size if it doesn't have a tile graphics."
            }
            DefaultLayer(
                initialPosition = offset,
                initialContents = tileGraphics {
                    size = this@LayerBuilder.size
                    tileset = this@LayerBuilder.tileset
                }
            )
        }.apply {
            if (filler != Tile.empty()) fill(filler)
        }
    }
}

public fun layer(init: LayerBuilder.() -> Unit): Layer =
    LayerBuilder().apply(init).build()



public fun LayerBuilder.withTileGraphics(init: TileGraphicsBuilder.() -> Unit): LayerBuilder = apply {
    tileGraphics = TileGraphicsBuilder().apply(init).build()
}

public fun LayerBuilder.withFiller(init: CharacterTileBuilder.() -> Unit): LayerBuilder = apply {
    filler = CharacterTileBuilder().apply(init).build()
}
