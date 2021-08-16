package org.hexworks.zircon.internal.data

import org.hexworks.zircon.api.data.base.BaseImageTile
import org.hexworks.zircon.api.resource.TilesetResource

data class DefaultImageTile internal constructor(
    override val name: String,
    override val tileset: TilesetResource
) : BaseImageTile() {

    override val cacheKey: String
        get() = "ImageTile(t=${tileset.path},n=$name)"

    override fun createCopy() = copy()

}
