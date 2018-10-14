package org.hexworks.zircon.internal.data

import org.hexworks.zircon.api.behavior.TilesetOverride
import org.hexworks.zircon.api.data.base.BaseImageTile
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.behavior.impl.DefaultTilesetOverride

data class DefaultImageTile(
        override val name: String,
        override val tileset: TilesetResource)
    : BaseImageTile(), TilesetOverride by DefaultTilesetOverride(tileset) {

    private val cacheKey = "ImageTile(t=${tileset.path},n=$name)"

    override fun createCopy() = copy()

    override fun generateCacheKey() = cacheKey

}
