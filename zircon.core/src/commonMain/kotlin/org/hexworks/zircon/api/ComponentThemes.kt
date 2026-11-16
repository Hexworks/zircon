package org.hexworks.zircon.api

import org.hexworks.zircon.api.component.theme.ComponentTheme
import org.hexworks.zircon.api.component.theme.NeobrutalismBuilder

/**
 * The built-in [ComponentTheme]s.
 */
public object ComponentThemes {

    /**
     * Neobrutalism: heavy borders, hard offset shadows, flat fills and one accent, after
     * HyperUI's collection. Needs a tileset with the Unicode block elements (every Nerd Font
     * and CP437 tileset has them).
     */
    public fun neobrutalism(init: NeobrutalismBuilder.() -> Unit = {}): ComponentTheme =
        NeobrutalismBuilder().apply(init).build()
}
