package org.hexworks.zircon.api

import org.hexworks.zircon.api.component.theme.ComponentPalette
import org.hexworks.zircon.api.component.theme.ThemeMode
import org.hexworks.zircon.internal.component.theme.neobrutalism.NeobrutalismPalettes

/**
 * The bundled [ComponentPalette]s. A palette is colors only; pair it with any
 * [ComponentTheme][org.hexworks.zircon.api.component.theme.ComponentTheme] through
 * `screen.componentPalette`.
 */
public object Palettes {

    /**
     * The colors HyperUI's Neobrutalism components use: black ink on white paper with a
     * yellow accent, or white on near-black with amber for [ThemeMode.DARK]. [ThemeMode.SYSTEM]
     * asks the platform.
     */
    public fun neobrutalism(mode: ThemeMode = ThemeMode.LIGHT): ComponentPalette = when (mode.resolve()) {
        ThemeMode.DARK -> NeobrutalismPalettes.dark
        else -> NeobrutalismPalettes.light
    }
}
