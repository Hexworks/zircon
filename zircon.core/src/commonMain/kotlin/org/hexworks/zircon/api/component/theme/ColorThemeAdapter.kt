@file:Suppress("DEPRECATION")

package org.hexworks.zircon.api.component.theme

import org.hexworks.zircon.api.color.extensions.desaturate
import org.hexworks.zircon.api.color.extensions.shade
import org.hexworks.zircon.api.color.extensions.tint
import org.hexworks.zircon.api.color.extensions.tone
import org.hexworks.zircon.api.component.ColorTheme

/**
 * This legacy five-color theme as a [ComponentPalette], so the bundled color themes keep
 * working. In the 1.x themes `secondaryBackgroundColor` is the screen and
 * `primaryBackgroundColor` the panels (`ColorThemes.gamebookers()`: grey screen, blue
 * panels), so they map to [PaletteKey.BACKGROUND] and [PaletteKey.SURFACE] in that order;
 * `accentColor` is the primary, the status colors are fixed.
 */
public fun ColorTheme.toComponentPalette(): ComponentPalette = componentPalette {
    name = this@toComponentPalette.name
    background = secondaryBackgroundColor
    onBackground = primaryForegroundColor
    surface = primaryBackgroundColor
    onSurface = primaryForegroundColor
    surfaceRaised = primaryBackgroundColor.tint(0.85)
    onSurfaceRaised = primaryForegroundColor
    surfaceSunken = secondaryBackgroundColor.shade(0.85)
    textMuted = secondaryForegroundColor
    textDisabled = secondaryForegroundColor.tone(0.6)
    border = secondaryForegroundColor
    borderFocused = accentColor
    primary = accentColor
    onPrimary = primaryBackgroundColor
    secondary = accentColor.desaturate(0.6)
    onSecondary = primaryBackgroundColor
    accent = accentColor
    onAccent = primaryBackgroundColor
    selection = accentColor.blend(secondaryBackgroundColor, 0.4)
    onSelection = primaryForegroundColor
    onInfo = secondaryBackgroundColor
    onSuccess = secondaryBackgroundColor
    onWarning = secondaryBackgroundColor
    onError = secondaryBackgroundColor
}

