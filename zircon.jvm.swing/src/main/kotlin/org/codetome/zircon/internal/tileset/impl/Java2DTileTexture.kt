package org.codetome.zircon.internal.tileset.impl

import org.codetome.zircon.api.tileset.TileTexture
import java.awt.image.BufferedImage

class Java2DTileTexture(
        private val cacheKey: String,
        private val backend: BufferedImage) : TileTexture<BufferedImage> {

    override fun generateCacheKey() = cacheKey

    override fun getBackend() = backend
}
