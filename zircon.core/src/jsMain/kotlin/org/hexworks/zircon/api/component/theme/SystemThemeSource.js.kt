package org.hexworks.zircon.api.component.theme

/**
 * Follows `prefers-color-scheme` live: a `change` on the media query updates [SystemTheme].
 */
internal actual fun installPlatformSystemThemeSource() {
    if (!hasMatchMedia()) return
    val query = js("window.matchMedia('(prefers-color-scheme: dark)')")
    query.addEventListener("change") { event: dynamic ->
        SystemTheme.update(mode = if (event.matches as Boolean) ThemeMode.DARK else ThemeMode.LIGHT)
    }
}
