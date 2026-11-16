@file:OptIn(ExperimentalForeignApi::class)

package org.hexworks.zircon.api.component.theme

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toKString
import platform.posix.getenv

/**
 * `gsettings` first (`color-scheme`, then `gtk-theme`), the `GTK_THEME` variable last.
 */
public actual fun platformSystemThemeDetector(): SystemThemeDetector = NativeSystemThemeDetector

private val NativeSystemThemeDetector: SystemThemeDetector = SystemThemeDetector {
    runNativeCommand("gsettings get org.gnome.desktop.interface color-scheme 2>/dev/null")
        ?.takeIf { it.first == 0 }?.second?.toThemeModeOrNull()
        ?: runNativeCommand("gsettings get org.gnome.desktop.interface gtk-theme 2>/dev/null")
            ?.takeIf { it.first == 0 }?.second?.toThemeModeOrNull()
        ?: getenv("GTK_THEME")?.toKString().toThemeModeOrNull()
}
