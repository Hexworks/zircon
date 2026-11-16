package org.hexworks.zircon.api.component.theme

/**
 * The four Catppuccin flavors: one light, three dark of increasing contrast.
 */
public enum class CatppuccinFlavor(public val isDark: Boolean) {
    LATTE(false),
    FRAPPE(true),
    MACCHIATO(true),
    MOCHA(true),
}

/**
 * Catppuccin's fourteen accent colors; the palette's `PRIMARY`. Catppuccin leaves the choice
 * to the user, so does `Palettes.catppuccin`.
 */
public enum class CatppuccinAccent {
    ROSEWATER, FLAMINGO, PINK, MAUVE, RED, MAROON, PEACH, YELLOW, GREEN, TEAL, SKY, SAPPHIRE, BLUE, LAVENDER,
}

/**
 * The four Tokyo Night styles: three dark, `DAY` light.
 */
public enum class TokyoNightStyle(public val isDark: Boolean) {
    NIGHT(true),
    STORM(true),
    MOON(true),
    DAY(false),
}

/**
 * The three Kanagawa variants: `WAVE` (dark), `DRAGON` (dark, muted), `LOTUS` (light).
 */
public enum class KanagawaVariant(public val isDark: Boolean) {
    WAVE(true),
    DRAGON(true),
    LOTUS(false),
}
