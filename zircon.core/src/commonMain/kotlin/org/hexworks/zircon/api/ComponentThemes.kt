package org.hexworks.zircon.api

import org.hexworks.zircon.api.component.theme.ComponentTheme
import org.hexworks.zircon.api.component.theme.NeobrutalismBuilder
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
     * Neobrutalism: heavy borders, hard offset shadows, flat fills and one accent, after
     * HyperUI's collection. Needs a tileset with the Unicode block elements (every Nerd Font
     * and CP437 tileset has them).
     */
    public fun neobrutalism(init: NeobrutalismBuilder.() -> Unit = {}): ComponentTheme =
        NeobrutalismBuilder().apply(init).build()
}
