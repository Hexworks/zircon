package org.hexworks.zircon.examples

import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.resource.BuiltInCP437TilesetResource.entries as CP437_TILESETS
import org.hexworks.zircon.internal.resource.BuiltInTrueTypeFontResource.entries as TTF_FONTS
import org.hexworks.zircon.api.Palettes
import org.hexworks.zircon.api.component.theme.ComponentPalette
import org.hexworks.zircon.api.ComponentThemes
import org.hexworks.zircon.api.component.theme.ComponentTheme
import org.hexworks.zircon.api.component.theme.ThemeMode

/**
 * The cell sizes the app offers, taken from the bundled CP437 sheets. Bigger cells than
 * [MAX_TILE_HEIGHT] leave too few of them in a window for the app's layout ([MIN_SIZE]).
 */
val TILE_SIZES: List<Size> = CP437_TILESETS
    .map { it.size }
    .filter { it.height <= MAX_TILE_HEIGHT }
    .distinct()
    .sortedWith(compareBy({ it.height }, { it.width }))

/** Every palette the app offers: the bundled ones. */
val PALETTES: List<ComponentPalette> = Palettes.all()

val DEFAULT_PALETTE: ComponentPalette = Palettes.catppuccin()

/**
 * What the palette selector offers: [System], the host's palette (the terminal's own colors,
 * or the light / dark member of the last picked family after the desktop setting), then every
 * bundled palette as a [Fixed] pick.
 */
sealed interface PaletteChoice {
    val name: String

    object System : PaletteChoice {
        override val name: String = "System"
    }

    data class Fixed(val palette: ComponentPalette) : PaletteChoice {
        override val name: String get() = palette.name
    }
}

val PALETTE_CHOICES: List<PaletteChoice> = listOf(PaletteChoice.System) + PALETTES.map { PaletteChoice.Fixed(it) }

/**
 * The light / dark pair [palette] belongs to (Catppuccin Latte / Mocha, Tokyo Night Day /
 * Night, Kanagawa Lotus / Wave, Solarized, Gruvbox, Neobrutalism light / dark), what [PaletteChoice.System] shows
 * where the host has no colors of its own. Catppuccin for anything else.
 */
fun paletteFamilyOf(palette: ComponentPalette): (ThemeMode) -> ComponentPalette = when {
    palette.name.startsWith("Tokyo Night") -> { mode -> Palettes.tokyoNight(mode) }
    palette.name.startsWith("Kanagawa") -> { mode -> Palettes.kanagawa(mode) }
    palette.name.startsWith("Solarized") -> { mode -> Palettes.solarized(mode) }
    palette.name.startsWith("Gruvbox") -> { mode -> Palettes.gruvbox(mode) }
    palette.name.startsWith("Neobrutalism") -> { mode -> Palettes.neobrutalism(mode) }
    else -> { mode -> Palettes.catppuccin(mode) }
}

/** The component themes (shapes) the app offers; the stock look first. */
val COMPONENT_THEMES: List<ComponentTheme> = listOf(ComponentThemes.default(), ComponentThemes.neobrutalism())

/**
 * Every bundled tileset with cells of [size]: the CP437 sheets of that size and the
 * TrueType fonts rendered at that height whose width works out to [size]'s width (the
 * narrow and wide fonts have their own ratio).
 */
fun tilesetsFor(size: Size): List<TilesetResource> =
    CP437_TILESETS.filter { it.size == size } +
        TTF_FONTS.map { it.toTilesetResource(size.height) }.filter { it.width == size.width }

private const val MAX_TILE_HEIGHT = 24
