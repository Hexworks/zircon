package org.hexworks.zircon.api

import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.hexworks.zircon.api.color.Color
import org.hexworks.zircon.api.component.theme.CatppuccinAccent
import org.hexworks.zircon.api.component.theme.CatppuccinFlavor
import org.hexworks.zircon.api.component.theme.KanagawaVariant
import org.hexworks.zircon.api.component.theme.PaletteKey
import org.hexworks.zircon.api.component.theme.ThemeMode
import org.hexworks.zircon.api.component.theme.TokyoNightStyle
import org.hexworks.zircon.api.component.theme.background
import org.hexworks.zircon.api.component.theme.isDark
import org.hexworks.zircon.api.component.theme.primary
import kotlin.test.Test

class PalettesTest {

    @Test
    fun every_bundled_palette_has_every_key_and_a_dark_flag_that_matches_its_background() {
        val all = Palettes.all()

        all shouldHaveSize 13
        all.map { it.name }.distinct() shouldHaveSize 13
        all.forEach { palette ->
            PaletteKey.entries.forEach { key -> palette[key] shouldNotBe null }
            palette.isDark shouldBe palette.background.isDark
        }
    }

    @Test
    fun the_mode_overloads_pick_the_light_or_dark_member_of_each_family() {
        Palettes.catppuccin(ThemeMode.LIGHT) shouldBe Palettes.catppuccin(CatppuccinFlavor.LATTE)
        Palettes.catppuccin(ThemeMode.DARK) shouldBe Palettes.catppuccin(CatppuccinFlavor.MOCHA)
        Palettes.tokyoNight(ThemeMode.LIGHT) shouldBe Palettes.tokyoNight(TokyoNightStyle.DAY)
        Palettes.tokyoNight(ThemeMode.DARK) shouldBe Palettes.tokyoNight(TokyoNightStyle.NIGHT)
        Palettes.kanagawa(ThemeMode.LIGHT) shouldBe Palettes.kanagawa(KanagawaVariant.LOTUS)
        Palettes.kanagawa(ThemeMode.DARK) shouldBe Palettes.kanagawa(KanagawaVariant.WAVE)
    }

    @Test
    fun catppuccin_takes_the_accent_as_the_primary() {
        // Mocha's hex values, from palette.json.
        Palettes.catppuccin(CatppuccinFlavor.MOCHA).primary shouldBe Color.fromString("#89b4fa")
        Palettes.catppuccin(CatppuccinFlavor.MOCHA, CatppuccinAccent.MAUVE).primary shouldBe Color.fromString("#cba6f7")
        Palettes.catppuccin(CatppuccinFlavor.MOCHA).background shouldBe Color.fromString("#1e1e2e")
        Palettes.catppuccin(CatppuccinFlavor.LATTE).background shouldBe Color.fromString("#eff1f5")
    }
}
