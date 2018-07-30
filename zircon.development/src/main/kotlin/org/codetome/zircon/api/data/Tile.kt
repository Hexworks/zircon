package org.codetome.zircon.api.data

import org.codetome.zircon.api.color.TextColor
import org.codetome.zircon.api.modifier.Modifier
import org.codetome.zircon.api.behavior.Drawable

interface Tile<T : Any> : Drawable<T> {

    fun getForegroundColor(): TextColor

    fun getBackgroundColor(): TextColor

    fun getModifiers(): Set<Modifier>

    val type: T

    fun keyType(): Class<out T> = type::class.java
}
