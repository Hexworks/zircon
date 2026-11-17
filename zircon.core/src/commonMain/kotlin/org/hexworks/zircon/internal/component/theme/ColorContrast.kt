package org.hexworks.zircon.internal.component.theme

import org.hexworks.zircon.api.color.Color
import kotlin.math.pow

/** WCAG relative luminance, 0 for black and 1 for white. */
internal fun Color.luminance(): Double {
    fun channel(value: Int): Double {
        val c = value / 255.0
        return if (c <= 0.03928) c / 12.92 else ((c + 0.055) / 1.055).pow(2.4)
    }
    return 0.2126 * channel(red) + 0.7152 * channel(green) + 0.0722 * channel(blue)
}

/** WCAG contrast ratio, 1 for equal colors and 21 for black on white. */
internal fun contrast(a: Color, b: Color): Double {
    val la = a.luminance()
    val lb = b.luminance()
    return (maxOf(la, lb) + 0.05) / (minOf(la, lb) + 0.05)
}

/**
 * Whichever of [candidates] contrasts more with [surface]: how a palette picks the text for
 * an accent it shares between its light and dark side, or whose luminance sits in the middle.
 */
internal fun readableOn(surface: Color, vararg candidates: Color): Color = candidates.maxBy { contrast(it, surface) }
