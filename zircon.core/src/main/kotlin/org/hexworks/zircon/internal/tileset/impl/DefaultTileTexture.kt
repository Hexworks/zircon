package org.hexworks.zircon.internal.tileset.impl

import org.hexworks.zircon.api.tileset.TileTexture

class DefaultTileTexture<T>(private val width: Int,
                            private val height: Int,
                            private val texture: T) : TileTexture<T> {

    override fun getWidth() = width

    override fun getHeight() = height

    override fun getTexture() = texture
}
