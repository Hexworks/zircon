package org.hexworks.zircon.internal.color

import org.hexworks.zircon.api.color.Color

internal data class DefaultColor(
    override val red: Int,
    override val green: Int,
    override val blue: Int,
    override val isTransparent: Boolean = false
) : Color {

    override val cacheKey: String = if (isTransparent) "Color(transparent)" else "Color(r=$red,g=$green,b=$blue)"

    override fun toString(): String = cacheKey
}
