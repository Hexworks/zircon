package org.hexworks.zircon.api.component.theme

import org.hexworks.zircon.api.color.Color

/**
 * The colors of the Neobrutalism theme, Tailwind names from HyperUI's source. [ink] draws
 * borders, shadows and text, [paper] fills; the dark variant swaps them and darkens the
 * accent.
 */
public data class NeobrutalismPalette(
    val ink: Color,
    val paper: Color,
    /** Hover and the active tab. */
    val accent: Color,
    /** The primary button's double border gap and the search button. */
    val accentStrong: Color,
    /** Text that carries less weight (dates, window controls) and disabled components. */
    val muted: Color,
    val infoBackground: Color,
    val infoText: Color,
    val successBackground: Color,
    val successText: Color,
    val errorBackground: Color,
    val errorText: Color,
    /** Status dots and progress fills. */
    val successDot: Color,
    val errorDot: Color,
) {
    public companion object {

        public fun light(): NeobrutalismPalette = NeobrutalismPalette(
            ink = Color.fromString("#000000"),
            paper = Color.fromString("#ffffff"),
            accent = Color.fromString("#fef08a"),
            accentStrong = Color.fromString("#fde047"),
            muted = Color.fromString("#9ca3af"),
            infoBackground = Color.fromString("#dbeafe"),
            infoText = Color.fromString("#1e3a8a"),
            successBackground = Color.fromString("#dcfce7"),
            successText = Color.fromString("#14532d"),
            errorBackground = Color.fromString("#fee2e2"),
            errorText = Color.fromString("#7f1d1d"),
            successDot = Color.fromString("#16a34a"),
            errorDot = Color.fromString("#dc2626"),
        )

        public fun dark(): NeobrutalismPalette = NeobrutalismPalette(
            ink = Color.fromString("#ffffff"),
            paper = Color.fromString("#111827"),
            accent = Color.fromString("#a16207"),
            accentStrong = Color.fromString("#ca8a04"),
            muted = Color.fromString("#6b7280"),
            infoBackground = Color.fromString("#1e40af"),
            infoText = Color.fromString("#eff6ff"),
            successBackground = Color.fromString("#166534"),
            successText = Color.fromString("#f0fdf4"),
            errorBackground = Color.fromString("#991b1b"),
            errorText = Color.fromString("#fef2f2"),
            successDot = Color.fromString("#86efac"),
            errorDot = Color.fromString("#fca5a5"),
        )

        public fun forMode(mode: ThemeMode): NeobrutalismPalette = when (mode.resolve()) {
            ThemeMode.DARK -> dark()
            else -> light()
        }
    }
}
