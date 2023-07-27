package org.hexworks.zircon.api.builder.graphics

import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.builder.data.CharacterTileBuilder
import org.hexworks.zircon.api.builder.data.PositionBuilder
import org.hexworks.zircon.api.builder.data.SizeBuilder
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.config.RuntimeConfig
import org.hexworks.zircon.internal.dsl.ZirconDsl
import org.hexworks.zircon.internal.graphics.DefaultLayer

/**
 * Use this to build [Layer]s. Defaults are:
 * - size: [Size.one()]
 * - filler: [Tile.empty()]
 * - offset: [Position.defaultPosition()]
 * - has no text image by default
 */
@ZirconDsl
class LayerBuilder : Builder<Layer> {

    var tileset: TilesetResource = RuntimeConfig.config.defaultTileset
    var size: Size = Size.unknown()
    var offset: Position = Position.defaultPosition()
    var tileGraphics: TileGraphics? = null
    var filler: Tile = Tile.empty()

    override fun build(): Layer {
        return tileGraphics?.let { graphics ->
            DefaultLayer(
                initialPosition = offset,
                initialContents = graphics
            )
        } ?: run {
            require(size.isNotUnknown) {
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

fun layer(init: LayerBuilder.() -> Unit) =
    LayerBuilder().apply(init).build()

fun LayerBuilder.withSize(init: SizeBuilder.() -> Unit) = apply {
    size = SizeBuilder().apply(init).build()
}

fun LayerBuilder.withOffset(init: PositionBuilder.() -> Unit) = apply {
    offset = PositionBuilder().apply(init).build()
}

fun LayerBuilder.withTileGraphics(init: TileGraphicsBuilder.() -> Unit) = apply {
    tileGraphics = TileGraphicsBuilder().apply(init).build()
}

fun LayerBuilder.withFiller(init: CharacterTileBuilder.() -> Unit) = apply {
    filler = CharacterTileBuilder().apply(init).build()
}
