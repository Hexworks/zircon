package org.hexworks.zircon.api.component.theme

import org.hexworks.zircon.api.component.Component

/**
 * A look for every [Component] on a screen: for each [ComponentRole] a [ThemeEntry] with the
 * renderer, the decorations and the colors per state. Themes are data: one generic
 * [ComponentFactory] builds components for any theme, and switching the theme of a screen
 * re-applies the entries to every attached component.
 *
 * A component's outer size is fixed when it is built; on a switch only its content area is
 * recomputed from the new decorations, so nothing moves. Size components for the heaviest
 * theme you plan to use, or build them through the [ComponentFactory] of the screen so the
 * theme's decorations are known when the size is computed.
 *
 * A component theme takes precedence over the [org.hexworks.zircon.api.component.ColorTheme]:
 * while a component has one, color theme changes are ignored for it.
 *
 * @see ComponentThemes for the built-in themes
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
     * The look for [role], or `null` when this theme has none, in which case the component
     * keeps what it has.
     */
    public fun entryFor(role: ComponentRole): ThemeEntry?

    /**
     * A [ComponentFactory] that pre-configures every builder with this theme.
     */
    public val componentFactory: ComponentFactory
        get() = ComponentFactory { this }
}
