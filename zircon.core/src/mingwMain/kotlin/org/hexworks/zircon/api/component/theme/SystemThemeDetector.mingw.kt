package org.hexworks.zircon.api.component.theme

/**
 * The `AppsUseLightTheme` registry value: `0x0` is dark, `0x1` light.
 */
public actual fun platformSystemThemeDetector(): SystemThemeDetector = NativeSystemThemeDetector

private val NativeSystemThemeDetector: SystemThemeDetector = SystemThemeDetector {
    val (status, output) = runNativeCommand(
        "reg query HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Themes\\Personalize /v AppsUseLightTheme 2>nul"
    ) ?: return@SystemThemeDetector null
    if (status != 0) return@SystemThemeDetector null
    val value = Regex("0x([0-9a-fA-F]+)").find(output)?.groupValues?.get(1)?.toIntOrNull(16)
    when (value) {
        null -> null
        0 -> ThemeMode.DARK
        else -> ThemeMode.LIGHT
    }
}
