package org.hexworks.zircon.api

import org.hexworks.zircon.api.color.Color
import org.hexworks.zircon.api.component.theme.CatppuccinAccent
import org.hexworks.zircon.api.component.theme.CatppuccinFlavor
import org.hexworks.zircon.api.component.theme.ComponentPalette
import org.hexworks.zircon.api.component.theme.KanagawaVariant
import org.hexworks.zircon.api.component.theme.ThemeMode
import org.hexworks.zircon.api.component.theme.TokyoNightStyle
import org.hexworks.zircon.internal.component.theme.AnsiPalettes
import org.hexworks.zircon.internal.component.theme.BundledPalettes
import org.hexworks.zircon.internal.component.theme.neobrutalism.NeobrutalismPalettes

/**
 * The bundled [ComponentPalette]s. A palette is colors only; pair it with any
 * [ComponentTheme][org.hexworks.zircon.api.component.theme.ComponentTheme] through
 * `screen.componentPalette`. Each family comes in light and dark variants; the `mode`
 * overloads pick one of them, [ThemeMode.SYSTEM] after the platform. The mapping of each
 * project's named colors onto the semantic keys follows its own style guide, see
 * `docs/research/semantic-component-palettes.md`.
 */
public object Palettes {

    /**
     * A palette from a terminal's colors: its default [foreground] and [background] and the
     * 16 [ansi] slots in SGR order (black, red, green, yellow, blue, magenta, cyan, white,
     * then their bright versions). Light or dark follows the background. The terminal
     * renderer reads these from the terminal and rebuilds the palette when they change.
     */
    public fun fromAnsi(
        foreground: Color,
        background: Color,
        ansi: List<Color>,
        name: String = "Terminal",
    ): ComponentPalette = AnsiPalettes.fromAnsi(foreground, background, ansi, name)

    /**
     * [Catppuccin](https://catppuccin.com) in one of its four flavors, with the chosen
     * [accent] as the primary (Catppuccin leaves that to the user; blue is its own default).
     */
    public fun catppuccin(
        flavor: CatppuccinFlavor = CatppuccinFlavor.MOCHA,
        accent: CatppuccinAccent = CatppuccinAccent.BLUE,
    ): ComponentPalette = BundledPalettes.catppuccin(flavor, accent)

    /**
     * Catppuccin Latte for [ThemeMode.LIGHT], Mocha for [ThemeMode.DARK].
     */
    public fun catppuccin(mode: ThemeMode, accent: CatppuccinAccent = CatppuccinAccent.BLUE): ComponentPalette =
        catppuccin(if (mode.resolve() == ThemeMode.DARK) CatppuccinFlavor.MOCHA else CatppuccinFlavor.LATTE, accent)

    /**
     * [Tokyo Night](https://github.com/folke/tokyonight.nvim) in one of its four styles.
     */
    public fun tokyoNight(style: TokyoNightStyle = TokyoNightStyle.NIGHT): ComponentPalette =
        BundledPalettes.tokyoNight(style)

    /**
     * Tokyo Night Day for [ThemeMode.LIGHT], Night for [ThemeMode.DARK].
     */
    public fun tokyoNight(mode: ThemeMode): ComponentPalette =
        tokyoNight(if (mode.resolve() == ThemeMode.DARK) TokyoNightStyle.NIGHT else TokyoNightStyle.DAY)

    /**
     * [Kanagawa](https://github.com/rebelot/kanagawa.nvim) in one of its three variants.
     */
    public fun kanagawa(variant: KanagawaVariant = KanagawaVariant.WAVE): ComponentPalette =
        BundledPalettes.kanagawa(variant)

    /**
     * Kanagawa Lotus for [ThemeMode.LIGHT], Wave for [ThemeMode.DARK].
     */
    public fun kanagawa(mode: ThemeMode): ComponentPalette =
        kanagawa(if (mode.resolve() == ThemeMode.DARK) KanagawaVariant.WAVE else KanagawaVariant.LOTUS)

    /**
     * [Solarized](https://ethanschoonover.com/solarized), dark for [ThemeMode.DARK] (the default),
     * light for [ThemeMode.LIGHT].
     */
    public fun solarized(mode: ThemeMode = ThemeMode.DARK): ComponentPalette =
        BundledPalettes.solarized(dark = mode.resolve() == ThemeMode.DARK)

    /**
     * [Gruvbox](https://github.com/morhetz/gruvbox) at medium contrast, dark for [ThemeMode.DARK]
     * (the default), light for [ThemeMode.LIGHT].
     */
    public fun gruvbox(mode: ThemeMode = ThemeMode.DARK): ComponentPalette =
        BundledPalettes.gruvbox(dark = mode.resolve() == ThemeMode.DARK)

    /**
     * Every bundled palette, for a picker: the Catppuccin flavors, the Tokyo Night styles, the
     * Kanagawa variants, Solarized and Gruvbox in both modes and the two Neobrutalism ones.
     */
    public fun all(): List<ComponentPalette> =
        CatppuccinFlavor.entries.map { catppuccin(it) } +
                TokyoNightStyle.entries.map { tokyoNight(it) } +
                KanagawaVariant.entries.map { kanagawa(it) } +
                listOf(solarized(ThemeMode.DARK), solarized(ThemeMode.LIGHT)) +
                listOf(gruvbox(ThemeMode.DARK), gruvbox(ThemeMode.LIGHT)) +
                listOf(neobrutalism(ThemeMode.LIGHT), neobrutalism(ThemeMode.DARK))

    /**
     * The colors HyperUI's Neobrutalism components use: black ink on white paper with a
     * yellow accent, or white on near-black with amber for [ThemeMode.DARK]. [ThemeMode.SYSTEM]
     * asks the platform.
     */
    public fun neobrutalism(mode: ThemeMode = ThemeMode.LIGHT): ComponentPalette = when (mode.resolve()) {
        ThemeMode.DARK -> NeobrutalismPalettes.dark
        else -> NeobrutalismPalettes.light
    }
}
