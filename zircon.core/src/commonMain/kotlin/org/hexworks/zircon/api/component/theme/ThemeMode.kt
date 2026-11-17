package org.hexworks.zircon.api.component.theme

/**
 * The light / dark variant of a [ComponentPalette], for palettes that come in both
 * (`Palettes.neobrutalism(mode)`). [SYSTEM] is the app's wish to follow the host: what the
 * host wants right now is [SystemTheme.mode], [DARK] when nothing can tell.
 */
public enum class ThemeMode {
    LIGHT,
    DARK,
    SYSTEM;

    /**
     * [LIGHT] or [DARK]: this mode, or for [SYSTEM] the host's current [SystemTheme.mode]. A
     * one-shot answer: to stay in step with the host, observe [SystemTheme.modeProperty] or use
     * `screen.followSystemPalette`.
     */
    public fun resolve(): ThemeMode = when (this) {
        SYSTEM -> SystemTheme.mode
        else -> this
    }
}
