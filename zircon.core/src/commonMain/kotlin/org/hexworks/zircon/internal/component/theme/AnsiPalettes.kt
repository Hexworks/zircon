package org.hexworks.zircon.internal.component.theme

import org.hexworks.zircon.api.color.Color
import org.hexworks.zircon.api.color.extensions.interpolateTo
import org.hexworks.zircon.api.component.theme.ComponentPalette
import org.hexworks.zircon.api.component.theme.componentPalette
import kotlin.math.pow

/**
 * Builds a [ComponentPalette] from what a terminal has: its default foreground and
 * background and the 16 ANSI slots. Surfaces are steps from the background towards the
 * foreground (a terminal UI is flat), the semantic colors come from the slots (blue is the
 * primary, cyan the accent, green / yellow / red / cyan the statuses), and every `ON_*`
 * color is whichever of foreground and background contrasts with its surface.
 */
internal object AnsiPalettes {

    private const val BLACK = 0
    private const val RED = 1
    private const val GREEN = 2
    private const val YELLOW = 3
    private const val BLUE = 4
    private const val MAGENTA = 5
    private const val CYAN = 6
    private const val BRIGHT_BLACK = 8

    fun fromAnsi(foreground: Color, background: Color, ansi: List<Color>, name: String): ComponentPalette {
        require(ansi.size == 16) { "A terminal has 16 ANSI colors, got ${ansi.size}." }
        val dark = background.luminance() < 0.5
        val towardsForeground = background.interpolateTo(foreground)
        fun step(ratio: Double): Color = towardsForeground.getColorAtRatio(ratio)
        val darkest = if (dark) background else foreground
        val lightest = if (dark) foreground else background
        fun on(surface: Color): Color = if (surface.luminance() > 0.4) darkest else lightest
        // Bright black is the usual "comment" grey; when it is too close to the background
        // (some themes make it the background itself) a step towards the foreground stands in.
        val muted = ansi[BRIGHT_BLACK].takeIf { contrast(it, background) > 1.6 } ?: step(0.55)
        return componentPalette {
            this.name = name
            isDark = dark
            this.background = background
            onBackground = foreground
            surface = background
            onSurface = foreground
            surfaceRaised = step(0.08)
            onSurfaceRaised = foreground
            surfaceSunken = step(0.04)
            textMuted = muted
            textDisabled = step(0.35)
            border = muted
            borderFocused = ansi[BLUE]
            primary = ansi[BLUE]
            onPrimary = on(ansi[BLUE])
            secondary = step(0.15)
            onSecondary = foreground
            accent = ansi[CYAN]
            onAccent = on(ansi[CYAN])
            selection = step(0.22)
            onSelection = foreground
            info = ansi[CYAN]
            onInfo = on(ansi[CYAN])
            success = ansi[GREEN]
            onSuccess = on(ansi[GREEN])
            warning = ansi[YELLOW]
            onWarning = on(ansi[YELLOW])
            error = ansi[RED]
            onError = on(ansi[RED])
        }
    }

    /** WCAG relative luminance, 0 for black and 1 for white. */
    fun Color.luminance(): Double {
        fun channel(value: Int): Double {
            val c = value / 255.0
            return if (c <= 0.03928) c / 12.92 else ((c + 0.055) / 1.055).pow(2.4)
        }
        return 0.2126 * channel(red) + 0.7152 * channel(green) + 0.0722 * channel(blue)
    }

    /** WCAG contrast ratio, 1 for equal colors and 21 for black on white. */
    fun contrast(a: Color, b: Color): Double {
        val la = a.luminance()
        val lb = b.luminance()
        return (maxOf(la, lb) + 0.05) / (minOf(la, lb) + 0.05)
    }
}
