package org.hexworks.zircon.api.component.theme

/**
 * Tells whether the operating system (or the browser) is set to a light or a dark theme.
 * [detect] returns `null` when it cannot tell, so callers can pick a fallback.
 *
 * [ZirconDefaults.systemThemeDetector][org.hexworks.zircon.api.config.ZirconDefaults.systemThemeDetector]
 * holds the one in effect; its default is [platformSystemThemeDetector], an `expect`/`actual`
 * utility that asks the platform (macOS `defaults`, GNOME `gsettings`, the Windows registry,
 * `prefers-color-scheme` in a browser).
 */
public fun interface SystemThemeDetector {

    /**
     * [ThemeMode.LIGHT], [ThemeMode.DARK], or `null` when unknown. Never [ThemeMode.SYSTEM].
     */
    public fun detect(): ThemeMode?

    public companion object {

        /**
         * A detector that never knows. Use it to force the fallback.
         */
        public val NONE: SystemThemeDetector = SystemThemeDetector { null }

        /**
         * A detector that always reports [mode].
         */
        public fun fixed(mode: ThemeMode): SystemThemeDetector {
            require(mode != ThemeMode.SYSTEM) { "A fixed detector needs LIGHT or DARK." }
            return SystemThemeDetector { mode }
        }
    }
}

/**
 * The [SystemThemeDetector] for the current platform. It reports `null` on platforms where
 * there is nothing to ask (a terminal, iOS from a library).
 */
public expect fun platformSystemThemeDetector(): SystemThemeDetector

/**
 * Maps the usual spellings of a system answer ("dark", "prefer-dark", "Dark", "0") to a mode.
 * Shared by the platform detectors.
 */
internal fun String?.toThemeModeOrNull(): ThemeMode? {
    val value = this?.trim()?.trim('\'', '"')?.lowercase() ?: return null
    return when {
        value.isEmpty() -> null
        "dark" in value -> ThemeMode.DARK
        "light" in value -> ThemeMode.LIGHT
        else -> null
    }
}
