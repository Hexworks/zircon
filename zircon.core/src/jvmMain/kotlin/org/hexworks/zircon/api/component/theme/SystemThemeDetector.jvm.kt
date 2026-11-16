package org.hexworks.zircon.api.component.theme

import java.util.concurrent.TimeUnit

public actual fun platformSystemThemeDetector(): SystemThemeDetector = JvmSystemThemeDetector

/**
 * Asks the desktop: `defaults` on macOS, `gsettings` (then `GTK_THEME`) on Linux and the
 * `AppsUseLightTheme` registry value on Windows. Every failure is `null`.
 */
internal object JvmSystemThemeDetector : SystemThemeDetector {

    override fun detect(): ThemeMode? {
        val os = System.getProperty("os.name")?.lowercase() ?: return null
        return try {
            when {
                "mac" in os || "darwin" in os -> detectMac()
                "win" in os -> detectWindows()
                else -> detectLinux()
            }
        } catch (e: Exception) {
            null
        }
    }

    private fun detectMac(): ThemeMode? {
        // The key is absent in light mode, so the command fails: that *is* the light answer.
        val (exit, out) = run("defaults", "read", "-g", "AppleInterfaceStyle") ?: return null
        return if (exit == 0) out.toThemeModeOrNull() ?: ThemeMode.LIGHT else ThemeMode.LIGHT
    }

    private fun detectWindows(): ThemeMode? {
        val (exit, out) = run(
            "reg", "query",
            "HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Themes\\Personalize",
            "/v", "AppsUseLightTheme"
        ) ?: return null
        if (exit != 0) return null
        return out.registryDwordToThemeMode()
    }

    private fun detectLinux(): ThemeMode? {
        run("gsettings", "get", "org.gnome.desktop.interface", "color-scheme")
            ?.takeIf { it.first == 0 }?.second?.toThemeModeOrNull()?.let { return it }
        run("gsettings", "get", "org.gnome.desktop.interface", "gtk-theme")
            ?.takeIf { it.first == 0 }?.second?.toThemeModeOrNull()?.let { return it }
        return System.getenv("GTK_THEME").toThemeModeOrNull()
    }

    private fun run(vararg command: String): Pair<Int, String>? = try {
        val process = ProcessBuilder(*command).redirectErrorStream(true).start()
        val finished = process.waitFor(2, TimeUnit.SECONDS)
        if (!finished) {
            process.destroyForcibly()
            null
        } else {
            process.exitValue() to process.inputStream.bufferedReader().readText()
        }
    } catch (e: Exception) {
        null
    }
}

/**
 * `AppsUseLightTheme    REG_DWORD    0x0` means dark, `0x1` light.
 */
internal fun String.registryDwordToThemeMode(): ThemeMode? {
    val value = Regex("0x([0-9a-fA-F]+)").find(this)?.groupValues?.get(1)?.toIntOrNull(16) ?: return null
    return if (value == 0) ThemeMode.DARK else ThemeMode.LIGHT
}
