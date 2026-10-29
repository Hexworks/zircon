package org.hexworks.zircon.api.builder.data

import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.data.ImageTile
import org.hexworks.zircon.api.dsl.ZirconDsl
import org.hexworks.zircon.api.modifier.Modifier
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.config.ZirconDefaults

/**
 * Builds [ImageTile]s.
 * Defaults:
 * - Default name is a space
 * - Default tileset comes from [ZirconDefaults]
 */
@ZirconDsl
public class ImageTileBuilder : Builder<ImageTile> {

    public var name: String = " "
    public var tileset: TilesetResource = ZirconDefaults.current.tileset
    public var modifiers: Set<Modifier> = setOf()

    override fun build(): ImageTile {
        return ImageTile(
            name = name,
            tileset = tileset,
            modifiers = modifiers
        )
    }
}

public fun imageTile(init: ImageTileBuilder.() -> Unit): ImageTile {
    return ImageTileBuilder().apply(init).build()
}