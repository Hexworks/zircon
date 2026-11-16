package org.hexworks.zircon.api.component.theme

/**
 * A look for every component kind: a [ThemeEntry] (renderer, decorations, style set) per
 * [ThemeKey]. Themes are data; one generic [ComponentFactory] builds components for any theme.
 */
public interface ComponentTheme {

    /**
     * A unique name, for display and debugging.
     */
    public val name: String

    /**
     * [ThemeMode.LIGHT] or [ThemeMode.DARK]; a theme resolves [ThemeMode.SYSTEM] when built.
     */
    public val mode: ThemeMode

    /**
     * The look for exactly [key], or `null` when this theme has none. Use [resolveEntry] to
     * look a component up with the fallbacks.
     */
    public fun entryFor(key: ThemeKey): ThemeEntry?

    /**
     * The look for a component: the first of [ThemeKey.fallbacks] this theme has an entry
     * for, or `null`, in which case the component keeps what it has. [kind] is what the
     * builder made (`LABEL` for a card's title), the last resort.
     */
    public fun resolveEntry(
        role: ComponentRole,
        variant: Variant = Variant.DEFAULT,
        part: Part = Part.NONE,
        kind: ComponentRole = role,
    ): ThemeEntry? = ThemeKey(role, variant, part).fallbacks(kind).firstNotNullOfOrNull { entryFor(it) }

    /**
     * A [ComponentFactory] that pre-configures every builder with this theme.
     */
    public val componentFactory: ComponentFactory
        get() = ComponentFactory { this }
}
