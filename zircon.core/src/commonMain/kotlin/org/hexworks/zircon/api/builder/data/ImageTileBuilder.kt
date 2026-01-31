package org.hexworks.zircon.api.builder.data

import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.data.ImageTile
import org.hexworks.zircon.api.dsl.ZirconDsl
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.config.RuntimeConfig

/**
 * Builds [ImageTile]s.
 * Defaults:
 * - Default name is a space
 * - Default tileset comes from [RuntimeConfig]
 */
@ZirconDsl
class ImageTileBuilder : Builder<ImageTile> {

    var name: String = " "
    var tileset: TilesetResource = RuntimeConfig.config.defaultTileset

    override fun build(): ImageTile {
        return ImageTile(
            name = name,
            tileset = tileset,
        )
    }
}

fun imageTile(init: ImageTileBuilder.() -> Unit): ImageTile {
    return ImageTileBuilder().apply(init).build()
}