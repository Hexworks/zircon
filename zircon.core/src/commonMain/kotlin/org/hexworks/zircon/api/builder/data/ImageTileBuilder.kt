package org.hexworks.zircon.api.builder.data

import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.data.ImageTile
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.config.RuntimeConfig
import org.hexworks.zircon.internal.data.DefaultImageTile
import org.hexworks.zircon.internal.dsl.ZirconDsl

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
        return DefaultImageTile(
            name = name,
            tileset = tileset,
        )
    }
}

fun imageTile(init: ImageTileBuilder.() -> Unit): ImageTile {
    return ImageTileBuilder().apply(init).build()
}