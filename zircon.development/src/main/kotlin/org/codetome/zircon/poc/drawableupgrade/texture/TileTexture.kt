package org.codetome.zircon.poc.drawableupgrade.texture

interface TileTexture<out T> {

    fun getWidth(): Int

    fun getHeight(): Int

    fun getTexture(): T
}
