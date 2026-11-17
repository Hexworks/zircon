package org.hexworks.zircon.internal.component.theme

import org.hexworks.zircon.api.color.Color
import org.hexworks.zircon.api.color.extensions.interpolateTo
import org.hexworks.zircon.api.component.theme.ComponentPalette
import org.hexworks.zircon.api.component.theme.componentPalette

/**
 * Builds a [ComponentPalette] from what a terminal has: its default foreground and
 * background and the 16 ANSI slots. Surfaces are steps from the background towards the
 * foreground (a terminal UI is flat), the semantic colors come from the slots (blue is the
 * primary, magenta the secondary, cyan the accent, cyan / green / yellow / red the statuses;
 * the bright slot stands in when the normal one is too close to the background), and every
 * `ON_*` color is whichever of foreground and background contrasts more with its surface,
 * or black / white when neither reads (Solarized's foreground on its own blue).
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
        fun on(surface: Color): Color {
            val own = listOf(background, foreground).maxBy { contrast(it, surface) }
            if (contrast(own, surface) >= READABLE) return own
            return listOf(BLACK_INK, WHITE_INK).maxBy { contrast(it, surface) }
        }
        // A slot that barely shows on the background (xterm's blue on black, a theme whose
        // normal colors are dim) gives way to its bright twin when that one shows better.
        fun slot(index: Int): Color =
            ansi[index].takeIf { contrast(it, background) >= READABLE }
                ?: listOf(ansi[index], ansi[index + 8]).maxBy { contrast(it, background) }
        // Bright black is the usual "comment" grey; when it is too close to the background
        // (some themes make it the background itself) a step towards the foreground stands in.
        val muted = ansi[BRIGHT_BLACK].takeIf { contrast(it, background) >= MUTED_READABLE } ?: step(0.55)
        val blue = slot(BLUE)
        val magenta = slot(MAGENTA)
        val cyan = slot(CYAN)
        val green = slot(GREEN)
        val yellow = slot(YELLOW)
        val red = slot(RED)
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
            borderFocused = blue
            primary = blue
            onPrimary = on(blue)
            secondary = magenta
            onSecondary = on(magenta)
            accent = cyan
            onAccent = on(cyan)
            selection = step(0.22)
            onSelection = foreground
            info = cyan
            onInfo = on(cyan)
            success = green
            onSuccess = on(green)
            warning = yellow
            onWarning = on(yellow)
            error = red
            onError = on(red)
        }
    }

    /** The contrast a text or accent color needs against its surface to count as readable. */
    private const val READABLE = 3.0

    /** Muted text may be fainter, but not invisible. */
    private const val MUTED_READABLE = 2.5
    private val BLACK_INK = Color.create(0, 0, 0)
    private val WHITE_INK = Color.create(255, 255, 255)
}
