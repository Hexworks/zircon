package org.codetome.zircon.internal.font.impl

import org.codetome.zircon.api.font.FontTextureRegion
import java.awt.image.BufferedImage

class Java2DFontTextureRegion(
        private val cacheKey: String,
        private val backend: BufferedImage) : FontTextureRegion<BufferedImage> {

    override fun generateCacheKey() = cacheKey

    override fun getBackend() = backend
}
