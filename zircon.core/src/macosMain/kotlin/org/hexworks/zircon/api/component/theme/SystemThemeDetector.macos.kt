package org.hexworks.zircon.api.component.theme

/**
 * `defaults read -g AppleInterfaceStyle` prints `Dark` in dark mode and fails in light mode
 * (the key is absent), so a failed command is the light answer.
 */
public actual fun platformSystemThemeDetector(): SystemThemeDetector = NativeSystemThemeDetector

private val NativeSystemThemeDetector: SystemThemeDetector = SystemThemeDetector {
    val (status, output) = runNativeCommand("defaults read -g AppleInterfaceStyle 2>&1")
        ?: return@SystemThemeDetector null
    if (status == 0) output.toThemeModeOrNull() ?: ThemeMode.LIGHT else ThemeMode.LIGHT
}
