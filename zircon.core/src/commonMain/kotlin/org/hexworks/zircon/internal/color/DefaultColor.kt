package org.hexworks.zircon.internal.color

import org.hexworks.zircon.api.color.ColorInterpolator
import org.hexworks.zircon.api.color.Color

internal data class DefaultColor(
    override val red: Int,
    override val green: Int,
    override val blue: Int,
    override val alpha: Int = Color.defaultAlpha()
) : Color {

    override val cacheKey = "TextColor(r=$red,g=$green,b=$blue,a=$alpha)"

    override fun toString() = "Color(r=$red, g:$green, b:$blue, a:$alpha)"

    override fun desaturate(factor: Double): Color {
        val l = 0.3 * red + 0.6 * green + 0.1 * blue
        val r = red + factor * (l - red)
        val g = green + factor * (l - green)
        val b = blue + factor * (l - blue)
        return DefaultColor(r.toInt(), g.toInt(), b.toInt(), alpha)
    }

    override fun tint(factor: Double): Color {
        requireRange(factor)
        val white = Color.create(255, 255, 255)
        return interpolateTo(white).getColorAtRatio(factor)
    }

    override fun shade(factor: Double): Color {
        requireRange(factor)
        val black = Color.create(0, 0, 0)
        return interpolateTo(black).getColorAtRatio(factor)
    }

    override fun tone(factor: Double): Color {
        requireRange(factor)
        val value = ((red + green + blue) / 3)
        val gray = Color.create(value, value, value)
        return interpolateTo(gray).getColorAtRatio(factor)
    }

    override fun invert(): Color {
        return DefaultColor(255 - red, 255 - green, 255 - blue, alpha)
    }

    override fun darkenByPercent(percentage: Double): Color {
        requireRange(percentage)
        return Color.create(
            red = (red * (1f - percentage)).toInt(),
            green = (green * (1f - percentage)).toInt(),
            blue = (blue * (1f - percentage)).toInt(),
            alpha = alpha
        )
    }

    override fun lightenByPercent(percentage: Double): Color {
        requireRange(percentage)
        return Color.create(
            red = (red * (1f + percentage)).toInt(),
            green = (green * (1f + percentage)).toInt(),
            blue = (blue * (1f + percentage)).toInt(),
            alpha = alpha
        )
    }

    private fun requireRange(percentage: Double) {
        require(percentage in 0.0..1.0) {
            "The given percentage ($percentage) is not in the required range (0 - 1)."
        }
    }

    override fun withAlpha(alpha: Int) = copy(alpha = alpha)

    override fun withRed(red: Int) = copy(red = red)

    override fun withGreen(green: Int) = copy(green = green)

    override fun withBlue(blue: Int) = copy(blue = blue)

    override fun interpolateTo(other: Color): ColorInterpolator = DefaultColorInterpolator(this, other)
}
