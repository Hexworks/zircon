package org.hexworks.zircon.api.color.extensions

import org.hexworks.zircon.api.color.Color
import org.hexworks.zircon.api.color.Color.Companion.DEFAULT_FACTOR
import org.hexworks.zircon.api.color.ColorInterpolator
import org.hexworks.zircon.internal.color.DefaultColor
import org.hexworks.zircon.internal.color.DefaultColorInterpolator

val Color.isOpaque: Boolean
    get() = alpha == 255

/**
 * Returns a new [Color] which is desaturated by the [DEFAULT_FACTOR] (.7).
 */
fun Color.desaturate(): Color = desaturate(DEFAULT_FACTOR)

/**
 * Returns a new [Color] which is desaturated by [factor].
 */
fun Color.desaturate(factor: Double): Color {
    val l = 0.3 * red + 0.6 * green + 0.1 * blue
    val r = red + factor * (l - red)
    val g = green + factor * (l - green)
    val b = blue + factor * (l - blue)
    return DefaultColor(r.toInt(), g.toInt(), b.toInt(), alpha)
}

/**
 * Returns a new [Color] which is tinted by the [DEFAULT_FACTOR] (.7).
 */
fun Color.tint(): Color = tint(Color.DEFAULT_FACTOR)

/**
 * Returns a new [Color] which is tinted by [factor].
 */
fun Color.tint(factor: Double): Color {
    requireRange(factor)
    val white = Color.create(255, 255, 255)
    return interpolateTo(white).getColorAtRatio(factor)
}

/**
 * Returns a new [Color] which is shaded by the [DEFAULT_FACTOR] (.7).
 */
fun Color.shade(): Color = shade(Color.DEFAULT_FACTOR)

/**
 * Returns a new [Color] which is shaded by [factor].
 */
fun Color.shade(factor: Double): Color {
    requireRange(factor)
    val black = Color.create(0, 0, 0)
    return interpolateTo(black).getColorAtRatio(factor)
}

/**
 * Returns a new [Color] which is toned by the [DEFAULT_FACTOR] (.7).
 */
fun Color.tone(): Color = tone(Color.DEFAULT_FACTOR)

/**
 * Returns a new [Color] which is toned by [factor].
 */
fun Color.tone(factor: Double): Color {
    requireRange(factor)
    val value = ((red + green + blue) / 3)
    val gray = Color.create(value, value, value)
    return interpolateTo(gray).getColorAtRatio(factor)
}

/**
 * Returns a new [Color] which is the inversion of this one.
 */
fun Color.invert(): Color {
    return DefaultColor(255 - red, 255 - green, 255 - blue, alpha)
}

/**
 * Returns a new [Color] which is darkened by the given [percentage].
 * The number must be between `0` and `1`.
 */
fun Color.darkenByPercent(percentage: Double): Color {
    requireRange(percentage)
    return Color.create(
        red = (red * (1f - percentage)).toInt(),
        green = (green * (1f - percentage)).toInt(),
        blue = (blue * (1f - percentage)).toInt(),
        alpha = alpha
    )
}

/**
 * Returns a new [Color] which is lightened by the given [percentage].
 * The number must be between `0` and `1`.
 */
fun Color.lightenByPercent(percentage: Double): Color {
    requireRange(percentage)
    return Color.create(
        red = (red * (1f + percentage)).toInt(),
        green = (green * (1f + percentage)).toInt(),
        blue = (blue * (1f + percentage)).toInt(),
        alpha = alpha
    )
}

/**
 * Creates a copy of this [Color] with the given [alpha].
 */
fun Color.withAlpha(alpha: Int): Color = DefaultColor(red, green, blue, alpha)

/**
 * Creates a copy of this [Color] with the given [red].
 */
fun Color.withRed(red: Int): Color = DefaultColor(red, green, blue, alpha)

/**
 * Creates a copy of this [Color] with the given [green].
 */
fun Color.withGreen(green: Int): Color = DefaultColor(red, green, blue, alpha)

/**
 * Creates a copy of this [Color] with the given [blue].
 */
fun Color.withBlue(blue: Int): Color = DefaultColor(red, green, blue, alpha)

/**
 * Creates a new [ColorInterpolator] with the receiver color as low color and the other color as high color.
 */
fun Color.interpolateTo(other: Color): ColorInterpolator = DefaultColorInterpolator(this, other)

private fun requireRange(percentage: Double) {
    require(percentage in 0.0..1.0) {
        "The given percentage ($percentage) is not in the required range (0 - 1)."
    }
}

