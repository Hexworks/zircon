package org.hexworks.zircon.internal.color

import org.hexworks.zircon.api.color.Color

/**
 * Channels are clamped to `0..255` on construction, so colour math that overshoots
 * (lightening white, interpolating past an end) never reaches a renderer out of range.
 */
internal class DefaultColor(
    red: Int,
    green: Int,
    blue: Int,
    override val isTransparent: Boolean = false
) : Color {

    override val red: Int = red.coerceIn(0, 255)
    override val green: Int = green.coerceIn(0, 255)
    override val blue: Int = blue.coerceIn(0, 255)

    override val cacheKey: String = if (isTransparent) "Color(transparent)" else "Color(r=${this.red},g=${this.green},b=${this.blue})"

    override fun equals(other: Any?): Boolean = other is DefaultColor &&
            red == other.red && green == other.green && blue == other.blue && isTransparent == other.isTransparent

    override fun hashCode(): Int = ((red * 31 + green) * 31 + blue) * 31 + isTransparent.hashCode()

    override fun toString(): String = cacheKey
}
