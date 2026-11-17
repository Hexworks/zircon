package org.hexworks.zircon.terminal

import org.hexworks.zircon.api.Palettes
import org.hexworks.zircon.api.color.Color
import org.hexworks.zircon.api.component.theme.ComponentPalette

/**
 * The colors a terminal reported: its default [foreground] and [background] (`OSC 10` and
 * `OSC 11`) and the 16 [ansi] slots (`OSC 4`), in SGR order. Slots the terminal did not
 * answer for are the xterm defaults. [runTerminal] reads them at startup and again when the
 * terminal says its theme changed, see `onTerminalColors`.
 */
public data class TerminalColors(
    val foreground: Color,
    val background: Color,
    val ansi: List<Color>,
) {
    init {
        require(ansi.size == 16) { "A terminal has 16 ANSI colors, got ${ansi.size}." }
    }

    /** `true` for a dark background. */
    public val isDark: Boolean
        get() = (0.2126 * background.red + 0.7152 * background.green + 0.0722 * background.blue) < 128

    /** A [ComponentPalette] in these colors, see [Palettes.fromAnsi]. */
    public fun toComponentPalette(name: String = "Terminal"): ComponentPalette =
        Palettes.fromAnsi(foreground, background, ansi, name)

    public companion object {
        /** The xterm defaults for the 16 slots, used for the ones a terminal does not report. */
        public val DEFAULT_ANSI: List<Color> = listOf(
            "#000000", "#cd0000", "#00cd00", "#cdcd00", "#0000ee", "#cd00cd", "#00cdcd", "#e5e5e5",
            "#7f7f7f", "#ff0000", "#00ff00", "#ffff00", "#5c5cff", "#ff00ff", "#00ffff", "#ffffff",
        ).map(Color::fromString)
    }
}
