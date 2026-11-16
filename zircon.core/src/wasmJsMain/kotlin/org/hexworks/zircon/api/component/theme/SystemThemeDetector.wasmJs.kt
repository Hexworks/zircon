package org.hexworks.zircon.api.component.theme

public actual fun platformSystemThemeDetector(): SystemThemeDetector = BrowserSystemThemeDetector

private object BrowserSystemThemeDetector : SystemThemeDetector {
    override fun detect(): ThemeMode? = if (hasMatchMedia()) {
        if (prefersDark()) ThemeMode.DARK else ThemeMode.LIGHT
    } else null
}

private fun hasMatchMedia(): Boolean =
    js("typeof window !== 'undefined' && typeof window.matchMedia === 'function'")

private fun prefersDark(): Boolean =
    js("window.matchMedia('(prefers-color-scheme: dark)').matches")
