package org.hexworks.zircon.internal.tileset.impl

import org.hexworks.zircon.api.tileset.TileTexture

class DefaultTileTexture<T>(
    override val width: Int,
    override val height: Int,
    override val texture: T,
    override val cacheKey: String
) : TileTexture<T>
