package org.hexworks.zircon.api

import org.hexworks.zircon.api.component.theme.ComponentTheme
import org.hexworks.zircon.internal.component.theme.neobrutalism.NeobrutalismTheme
import org.hexworks.zircon.internal.component.theme.DefaultComponentTheme

/**
 * The built-in [ComponentTheme]s.
 */
public object ComponentThemes {

    /**
     * The stock look: what every builder starts from and what every other theme falls back
     * to, key by key. It has no colors of its own; those come from the
     * [ColorTheme][org.hexworks.zircon.api.component.ColorTheme]. Set it on a screen to
     * undo another theme.
     */
    public fun default(): ComponentTheme = DefaultComponentTheme

    /**
     * Neobrutalism: heavy borders, hard offset shadows, flat fills, after HyperUI's
     * collection. Shape only: pair it with `Palettes.neobrutalism(mode)` for HyperUI's colors,
     * or any other palette. Needs a tileset with the heavy box drawing and block element
     * glyphs (a TrueType or Nerd Font tileset; CP437 sheets lack them).
     */
    public fun neobrutalism(): ComponentTheme = NeobrutalismTheme
}
