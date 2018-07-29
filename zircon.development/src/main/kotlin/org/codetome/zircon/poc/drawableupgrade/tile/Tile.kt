package org.codetome.zircon.poc.drawableupgrade.tile

import org.codetome.zircon.poc.drawableupgrade.drawables.Drawable

interface Tile<T : Any> : Drawable<T> {

    val type: T

    fun keyType(): Class<out T> = type::class.java
}
