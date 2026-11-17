package org.hexworks.zircon.api.component.theme

import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.hexworks.zircon.api.Palettes
import org.hexworks.zircon.api.color.Color
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
        palette.textMuted shouldBe ansi[8]
        // Light slots get dark text on them.
        palette.onPrimary shouldBe palette.background
        palette.onWarning shouldBe palette.background
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
