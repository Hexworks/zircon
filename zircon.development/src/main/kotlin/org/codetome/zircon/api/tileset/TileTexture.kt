package org.codetome.zircon.api.tileset

interface TileTexture<out T> {

    fun getWidth(): Int

    fun getHeight(): Int

    fun getTexture(): T
}
