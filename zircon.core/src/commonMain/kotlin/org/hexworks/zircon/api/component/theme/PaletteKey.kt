package org.hexworks.zircon.api.component.theme

/**
 * The semantic colors a [ComponentPalette] supplies. Themes derive every component's
 * [ComponentStyleSet][org.hexworks.zircon.api.component.ComponentStyleSet] from these; a
 * palette never names a component. `ON_*` is the color drawn on the surface of the same name
 * (a tile has one foreground and one background, so every pair is explicit).
 */
public enum class PaletteKey {
    /** The screen. */
    BACKGROUND,
    /** Body text on the screen. */
    ON_BACKGROUND,

    /** Panels, cards, dialogs: one step up from the screen. */
    SURFACE,
    ON_SURFACE,

    /** Popups, modals, tooltips: two steps up. */
    SURFACE_RAISED,
    ON_SURFACE_RAISED,

    /** Wells, input fields, status bars: one step down. */
    SURFACE_SUNKEN,

    /** Labels, hints. */
    TEXT_MUTED,
    /** Disabled text. */
    TEXT_DISABLED,

    /** Frame glyphs at rest. */
    BORDER,
    /** Frame glyphs of the focused component. */
    BORDER_FOCUSED,

    /** The brand, the main action. */
    PRIMARY,
    ON_PRIMARY,

    /** A less emphasized action. */
    SECONDARY,
    ON_SECONDARY,

    /** Highlights, hover surfaces, badges. */
    ACCENT,
    ON_ACCENT,

    /** The selected row, the selected tab, marked text. */
    SELECTION,
    ON_SELECTION,

    INFO,
    ON_INFO,
    SUCCESS,
    ON_SUCCESS,
    WARNING,
    ON_WARNING,
    ERROR,
    ON_ERROR,
}
