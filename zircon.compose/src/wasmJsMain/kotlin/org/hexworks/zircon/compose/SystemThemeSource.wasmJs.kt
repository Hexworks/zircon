package org.hexworks.zircon.compose

import org.hexworks.zircon.api.component.theme.ThemeMode
import org.hexworks.zircon.api.config.ZirconDefaults

/** A browser answers `prefers-color-scheme`, and core listens for its changes already. */
internal actual fun hostReportsSystemTheme(): Boolean = true

internal actual suspend fun detectSystemTheme(): ThemeMode? = ZirconDefaults.current.systemThemeDetector.detect()
