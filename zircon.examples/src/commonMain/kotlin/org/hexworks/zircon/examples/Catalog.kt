package org.hexworks.zircon.examples

import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.resource.BuiltInCP437TilesetResource.entries as CP437_TILESETS
import org.hexworks.zircon.internal.resource.BuiltInTrueTypeFontResource.entries as TTF_FONTS
import org.hexworks.zircon.api.Palettes
import org.hexworks.zircon.api.component.theme.ComponentPalette
import org.hexworks.zircon.api.ComponentThemes
import org.hexworks.zircon.api.component.theme.ComponentTheme

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
