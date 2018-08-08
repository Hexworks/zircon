package org.codetome.zircon.internal.tileset.impl

import org.codetome.zircon.api.tileset.TileTexture

class DefaultTileTexture<T>(private val width: Int,
                            private val height: Int,
                            private val texture: T) : TileTexture<T> {

    override fun getWidth() = width

    override fun getHeight() = height

    override fun getTexture() = texture
}
