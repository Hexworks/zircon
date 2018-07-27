package org.codetome.zircon.poc.drawableupgrade.tileset

class DefaultTileTexture<T>(private val texture: T) : TileTexture<T> {

    override fun getTexture() = texture
}
