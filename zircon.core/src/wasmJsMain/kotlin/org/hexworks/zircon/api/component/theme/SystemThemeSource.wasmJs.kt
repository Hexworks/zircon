package org.hexworks.zircon.api.component.theme

/**
 * Follows `prefers-color-scheme` live: a `change` on the media query updates [SystemTheme].
 */
internal actual fun installPlatformSystemThemeSource() {
    if (!hasMatchMedia()) return
    onColorSchemeChange { dark -> SystemTheme.update(mode = if (dark) ThemeMode.DARK else ThemeMode.LIGHT) }
}

private fun onColorSchemeChange(callback: (Boolean) -> Unit): Unit =
    js("window.matchMedia('(prefers-color-scheme: dark)').addEventListener('change', function (e) { callback(e.matches); })")
