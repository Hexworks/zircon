package org.hexworks.zircon.compose

import org.hexworks.zircon.api.component.theme.ThemeMode

/**
 * `true` when Compose's `isSystemInDarkTheme()` is a real answer on this host (macOS, Windows,
 * a browser); `false` where it is a constant (Linux desktop), so [TileGridView] samples
 * Zircon's own detector instead.
 */
internal expect fun hostReportsSystemTheme(): Boolean

/**
 * Asks `ZirconDefaults.systemThemeDetector`, off the UI thread where the platform can (the
 * desktop detectors shell out).
 */
internal expect suspend fun detectSystemTheme(): ThemeMode?

/** How often [TileGridView] re-samples the detector where the host reports nothing. */
internal const val SYSTEM_THEME_POLL_MS: Long = 2000
