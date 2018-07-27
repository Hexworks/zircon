package org.codetome.zircon.internal.tileset.impl

import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.codetome.zircon.api.tileset.TileTexture

class LibgdxTileTexture(private val cacheKey: String,
                        private val backend: TextureRegion): TileTexture<TextureRegion> {

    override fun generateCacheKey() = cacheKey

    override fun getBackend() = backend
}
