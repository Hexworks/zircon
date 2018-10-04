package org.hexworks.zircon.internal.data

import org.hexworks.zircon.api.behavior.TilesetOverride
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.data.ImageTile
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.modifier.Modifier
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.behavior.impl.DefaultTilesetOverride

data class DefaultImageTile(
        override val tileset: TilesetResource,
        override val name: String,
        private val style: StyleSet = StyleSet.defaultStyle(),
        private val tilesetOverride: TilesetOverride = DefaultTilesetOverride(tileset))
    : ImageTile,
        TilesetOverride by tilesetOverride {

    private val cacheKey = "ImageTile(t=${tileset.path},n=$name)"

    override fun createCopy() = copy()

    override fun generateCacheKey() = cacheKey

    override fun withName(name: String) = DefaultImageTile(
            name = name,
            tileset = tileset,
            style = style)


}
