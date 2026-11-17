package org.hexworks.zircon.api.component.theme

public actual fun platformSystemThemeDetector(): SystemThemeDetector = BrowserSystemThemeDetector

private object BrowserSystemThemeDetector : SystemThemeDetector {
    override fun detect(): ThemeMode? = if (hasMatchMedia()) {
        if (prefersDark()) ThemeMode.DARK else ThemeMode.LIGHT
    } else null
}

internal fun hasMatchMedia(): Boolean =
    js("typeof window !== 'undefined' && typeof window.matchMedia === 'function'") as Boolean

private fun prefersDark(): Boolean =
    js("window.matchMedia('(prefers-color-scheme: dark)').matches") as Boolean
