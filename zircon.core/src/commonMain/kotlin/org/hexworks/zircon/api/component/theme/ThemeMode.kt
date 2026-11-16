package org.hexworks.zircon.api.component.theme

import org.hexworks.zircon.api.config.ZirconDefaults

/**
 * The light / dark variant of a [ComponentTheme]. [SYSTEM] follows the operating system
 * (or the browser) and falls back to [DARK] when the platform cannot tell.
 */
public enum class ThemeMode {
    LIGHT,
    DARK,
    SYSTEM;

    /**
     * [LIGHT] or [DARK]: this mode, or for [SYSTEM] what [ZirconDefaults.systemThemeDetector]
     * reports (and [DARK] when it reports nothing).
     */
    public fun resolve(): ThemeMode = when (this) {
        SYSTEM -> ZirconDefaults.current.systemThemeDetector.detect() ?: DARK
        else -> this
    }
}
