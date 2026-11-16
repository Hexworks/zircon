package org.hexworks.zircon.api.component.theme

import org.hexworks.zircon.api.ComponentThemes

/**
 * A look for every component kind: a [ThemeEntry] (renderer, decorations, and how the colors
 * derive from the screen's [ComponentPalette]) per [ThemeKey]. A theme is shape only; light
 * and dark, brand colors and the rest are the palette's, so one theme works with any palette.
 * Themes are data; one generic [ComponentFactory] builds components for any theme.
 *
 * A theme is always in effect: [ComponentThemes.default()][org.hexworks.zircon.api.ComponentThemes.default]
 * holds the stock look, and every other theme falls back to it, key by key and aspect by
 * aspect, so a theme only has to say what it changes.
 */
public interface ComponentTheme {

    /**
     * A unique name, for display and debugging.
     */
    public val name: String

    /**
     * The look for exactly [key], or `null` when this theme has none. Use [resolveEntry] to
     * look a component up with the fallbacks.
     */
    public fun entryFor(key: ThemeKey): ThemeEntry?

    /**
     * The look for a component: the entries this theme has for [ThemeKey.fallbacks], the
     * most specific first, each filling in what the one before left `null`, and then the
     * default theme's entries the same way. `null` only for [ComponentRole.NONE], which is
     * never themed, or a [kind] no theme knows; the component then keeps what it has.
     * [kind] is what the builder made (`LABEL` for a card's title), the last resort.
     */
    public fun resolveEntry(
        role: ComponentRole,
        variant: Variant = Variant.DEFAULT,
        part: Part = Part.NONE,
        kind: ComponentRole = role,
    ): ThemeEntry? {
        val keys = ThemeKey(role, variant, part).fallbacks(kind)
        val default = ComponentThemes.default()
        val chain = keys.mapNotNull { entryFor(it) } +
                if (this === default) emptyList() else keys.mapNotNull { default.entryFor(it) }
        return chain.reduceOrNull { entry, base -> entry over base }
    }

    /**
     * A [ComponentFactory] that pre-configures every builder with this theme.
     */
    public val componentFactory: ComponentFactory
        get() = ComponentFactory { this }
}
