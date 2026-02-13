package org.hexworks.zircon.internal.color

import org.hexworks.zircon.api.color.Color

internal data class DefaultColor(
    override val red: Int,
    override val green: Int,
    override val blue: Int,
    override val alpha: Int = Color.DEFAULT_ALPHA
) : Color {

    override val cacheKey = "Color(r=$red,g=$green,b=$blue,a=$alpha)"

    override fun toString() = "Color(r=$red, g:$green, b:$blue, a:$alpha)"

}
