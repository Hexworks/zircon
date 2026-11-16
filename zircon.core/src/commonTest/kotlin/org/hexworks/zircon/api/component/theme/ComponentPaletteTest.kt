@file:Suppress("DEPRECATION")

package org.hexworks.zircon.api.component.theme

import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.types.shouldBeSameInstanceAs
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.ComponentThemes
import org.hexworks.zircon.api.Palettes
import org.hexworks.zircon.api.builder.component.button
import org.hexworks.zircon.api.builder.component.panel
import org.hexworks.zircon.api.builder.grid.tileGrid
import org.hexworks.zircon.api.color.Color
import org.hexworks.zircon.api.component.data.ComponentState
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.extensions.toScreen
import kotlin.test.Test
import kotlin.test.assertFailsWith

class ComponentPaletteTest {

    private val gamebookers = ColorThemes.gamebookers()

    @Test
    fun a_color_theme_adapts_to_a_palette_with_the_screen_color_as_the_background() {
        val palette = gamebookers.toComponentPalette()

        palette.name shouldBe "Gamebookers"
        palette.isDark shouldBe true
        palette.background shouldBe gamebookers.secondaryBackgroundColor
        palette.surface shouldBe gamebookers.primaryBackgroundColor
        palette.onBackground shouldBe gamebookers.primaryForegroundColor
        palette.textMuted shouldBe gamebookers.secondaryForegroundColor
        palette.border shouldBe gamebookers.secondaryForegroundColor
        palette.primary shouldBe gamebookers.accentColor
        palette.onPrimary shouldBe gamebookers.primaryBackgroundColor
    }

    @Test
    fun a_palette_needs_three_colors_and_derives_the_rest() {
        val palette = componentPalette {
            background = Color.create(10, 10, 10)
            onBackground = Color.create(240, 240, 240)
            primary = Color.create(255, 0, 0)
        }

        palette.isDark shouldBe true
        PaletteKey.entries.forEach { palette[it] shouldNotBe null }
        palette.onPrimary shouldBe palette.background
        palette.borderFocused shouldBe palette.primary
        assertFailsWith<IllegalArgumentException> { componentPalette { background = Color.create(0, 0, 0) } }
    }

    @Test
    fun with_primary_replaces_only_the_primary_pair() {
        val base = Palettes.neobrutalism(ThemeMode.LIGHT)
        val red = Color.create(200, 0, 0)

        val changed = base.withPrimary(red, Color.create(255, 255, 255))

        changed.primary shouldBe red
        changed.onPrimary shouldBe Color.create(255, 255, 255)
        changed.background shouldBe base.background
        changed.accent shouldBe base.accent
        base.primary shouldNotBe red
    }

    @Test
    fun an_override_replaces_what_the_theme_derived_for_one_slot() {
        val palette = componentPalette {
            background = Color.create(0, 0, 0)
            onBackground = Color.create(255, 255, 255)
            primary = Color.create(0, 0, 255)
            override(ComponentRole.BUTTON, ComponentState.HIGHLIGHTED) { foregroundColor = Color.create(1, 2, 3) }
        }
        val entry = ComponentThemes.default().resolveEntry(ComponentRole.BUTTON)!!

        val styles = entry.stylesFor(palette, ComponentRole.BUTTON)!!

        styles.fetchStyleFor(ComponentState.HIGHLIGHTED).foregroundColor shouldBe Color.create(1, 2, 3)
        styles.fetchStyleFor(ComponentState.DEFAULT).foregroundColor shouldBe palette.primary
        // Another component is untouched.
        ComponentThemes.default().resolveEntry(ComponentRole.LABEL)!!.stylesFor(palette, ComponentRole.LABEL)!!
            .fetchStyleFor(ComponentState.HIGHLIGHTED).foregroundColor shouldBe palette.textMuted
    }

    @Test
    fun the_screen_palette_reaches_an_attached_component_and_a_switch_recolors_it() {
        val screen = tileGrid { size = Size(40, 20) }.toScreen()
        val button = button { +"OK" }
        screen.addComponent(button)
        val before = button.componentStyleSet

        screen.componentPalette = Palettes.neobrutalism(ThemeMode.LIGHT)

        button.componentPalette shouldBeSameInstanceAs Palettes.neobrutalism(ThemeMode.LIGHT)
        button.componentStyleSet shouldNotBe before
        button.componentStyleSet.fetchStyleFor(ComponentState.DEFAULT).foregroundColor shouldBe
                Palettes.neobrutalism(ThemeMode.LIGHT).primary
    }

    @Test
    fun an_explicit_palette_is_pinned_against_the_parent() {
        val screen = tileGrid { size = Size(40, 20) }.toScreen()
        val pinned = panel {
            preferredSize = Size(10, 5)
            componentPalette = Palettes.neobrutalism(ThemeMode.DARK)
        }
        val following = panel {
            preferredSize = Size(10, 5)
            position = Position(10, 0)
        }
        screen.addComponent(pinned)
        screen.addComponent(following)

        screen.componentPalette = Palettes.neobrutalism(ThemeMode.LIGHT)

        pinned.componentPalette shouldBeSameInstanceAs Palettes.neobrutalism(ThemeMode.DARK)
        following.componentPalette shouldBeSameInstanceAs Palettes.neobrutalism(ThemeMode.LIGHT)
    }

    @Test
    fun setting_the_legacy_color_theme_on_a_screen_switches_its_palette() {
        val screen = tileGrid { size = Size(40, 20) }.toScreen()
        val button = button { +"OK" }
        screen.addComponent(button)

        screen.theme = ColorThemes.afterglow()

        screen.componentPalette shouldBe ColorThemes.afterglow().toComponentPalette()
        button.componentStyleSet.fetchStyleFor(ComponentState.DEFAULT).foregroundColor shouldBe
                ColorThemes.afterglow().accentColor
    }

    @Test
    fun the_theme_derives_the_colors_and_the_palette_supplies_them() {
        val light = Palettes.neobrutalism(ThemeMode.LIGHT)
        val dark = Palettes.neobrutalism(ThemeMode.DARK)
        val entry = ComponentThemes.neobrutalism().resolveEntry(ComponentRole.BUTTON)!!

        entry.stylesFor(light, ComponentRole.BUTTON)!!.fetchStyleFor(ComponentState.DEFAULT).backgroundColor shouldBe light.surface
        entry.stylesFor(dark, ComponentRole.BUTTON)!!.fetchStyleFor(ComponentState.DEFAULT).backgroundColor shouldBe dark.surface
        light.isDark shouldBe false
        dark.isDark shouldBe true
    }
}
