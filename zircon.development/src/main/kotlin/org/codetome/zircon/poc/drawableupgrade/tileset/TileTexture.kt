package org.codetome.zircon.poc.drawableupgrade.tileset

interface TileTexture<out T> {

    fun getTexture(): T
}
