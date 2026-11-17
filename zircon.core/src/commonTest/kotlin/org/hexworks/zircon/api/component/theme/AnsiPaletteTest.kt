package org.hexworks.zircon.api.component.theme

import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.hexworks.zircon.api.Palettes
import org.hexworks.zircon.api.color.Color
import org.hexworks.zircon.internal.component.theme.contrast
import kotlin.test.Test

class AnsiPaletteTest {

    private val ansi = listOf(
        "#1e1e2e", "#f38ba8", "#a6e3a1", "#f9e2af", "#89b4fa", "#f5c2e7", "#94e2d5", "#bac2de",
        "#585b70", "#f38ba8", "#a6e3a1", "#f9e2af", "#89b4fa", "#f5c2e7", "#94e2d5", "#a6adc8",
    ).map(Color::fromString)

    @Test
    fun a_dark_terminal_gives_a_dark_palette_with_the_slots_as_statuses() {
        val palette = Palettes.fromAnsi(
            foreground = Color.fromString("#cdd6f4"),
            background = Color.fromString("#1e1e2e"),
            ansi = ansi,
        )
        palette.name shouldBe "Terminal"
        palette.isDark shouldBe true
        palette.background shouldBe Color.fromString("#1e1e2e")
        palette.onBackground shouldBe Color.fromString("#cdd6f4")
        palette.primary shouldBe ansi[4]
        palette.success shouldBe ansi[2]
        palette.warning shouldBe ansi[3]
        palette.error shouldBe ansi[1]
        palette.info shouldBe ansi[6]
        palette.secondary shouldBe ansi[5]
        // Bright black (surface2) is only 2:1 on this background: a step toward the foreground stands in.
        palette.textMuted shouldNotBe ansi[8]
        (contrast(palette.textMuted, palette.background) >= 2.5) shouldBe true
        // Light slots get dark text on them.
        palette.onPrimary shouldBe palette.background
        palette.onWarning shouldBe palette.background
    }

    @Test
    fun a_slot_that_barely_shows_gives_way_to_its_bright_twin_and_text_that_does_not_read_to_ink() {
        // xterm: blue 4 is #0000ee on black, Solarized Dark: the foreground is grey on every accent.
        val xterm = Palettes.fromAnsi(
            foreground = Color.fromString("#ffffff"),
            background = Color.fromString("#000000"),
            ansi = TerminalDefaults,
        )
        xterm.primary shouldBe Color.fromString("#5c5cff")
        val solarized = Palettes.fromAnsi(
            foreground = Color.fromString("#839496"),
            background = Color.fromString("#002b36"),
            ansi = listOf(
                "#073642", "#dc322f", "#859900", "#b58900", "#268bd2", "#d33682", "#2aa198", "#eee8d5",
                "#002b36", "#cb4b16", "#586e75", "#657b83", "#839496", "#6c71c4", "#93a1a1", "#fdf6e3",
            ).map(Color::fromString),
        )
        solarized.primary shouldBe Color.fromString("#268bd2")
        solarized.onPrimary shouldBe Color.fromString("#002b36")
        (contrast(solarized.onInfo, solarized.info) >= 3.0) shouldBe true
        (contrast(solarized.onError, solarized.error) >= 3.0) shouldBe true
    }

    @Test
    fun a_light_terminal_gives_a_light_palette_and_falls_back_when_bright_black_is_the_background() {
        val background = Color.fromString("#ffffff")
        val palette = Palettes.fromAnsi(
            foreground = Color.fromString("#202020"),
            background = background,
            ansi = ansi.toMutableList().also { it[8] = background },
            name = "Paper",
        )
        palette.name shouldBe "Paper"
        palette.isDark shouldBe false
        palette.textMuted shouldNotBe background
        palette.onError shouldBe Color.fromString("#202020")
        palette.surfaceRaised shouldNotBe background
    }
}

private val TerminalDefaults = listOf(
    "#000000", "#cd0000", "#00cd00", "#cdcd00", "#0000ee", "#cd00cd", "#00cdcd", "#e5e5e5",
    "#7f7f7f", "#ff0000", "#00ff00", "#ffff00", "#5c5cff", "#ff00ff", "#00ffff", "#ffffff",
).map(Color::fromString)
