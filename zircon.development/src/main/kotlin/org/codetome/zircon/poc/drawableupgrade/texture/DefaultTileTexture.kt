package org.codetome.zircon.poc.drawableupgrade.texture

class DefaultTileTexture<T>(private val width: Int,
                            private val height: Int,
                            private val texture: T) : TileTexture<T> {

    override fun getWidth() = width

    override fun getHeight() = height

    override fun getTexture() = texture
}
