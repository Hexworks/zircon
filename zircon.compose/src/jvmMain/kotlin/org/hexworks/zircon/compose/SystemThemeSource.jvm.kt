package org.hexworks.zircon.compose

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.hexworks.zircon.api.component.theme.ThemeMode
import org.hexworks.zircon.api.config.ZirconDefaults
import org.jetbrains.skiko.currentSystemTheme
import org.jetbrains.skiko.SystemTheme as SkikoSystemTheme

/** Skiko knows the theme on macOS and Windows; on Linux it answers `UNKNOWN`. */
internal actual fun hostReportsSystemTheme(): Boolean = currentSystemTheme != SkikoSystemTheme.UNKNOWN

internal actual suspend fun detectSystemTheme(): ThemeMode? = withContext(Dispatchers.IO) {
    ZirconDefaults.current.systemThemeDetector.detect()
}
