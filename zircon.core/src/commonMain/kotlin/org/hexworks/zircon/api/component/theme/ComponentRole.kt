package org.hexworks.zircon.api.component.theme

/**
 * What a component *is* for a [ComponentTheme]: the widget kind, or the composite fragment it
 * is a part of. Builders set the kind; fragments set the composite on their parts and say
 * which part with a [Part]. The look of a role is further selected by a [Variant].
 *
 * The enum is closed: a custom component falls back to the role its builder gives it.
 */
public enum class ComponentRole {
    /** Never themed. For layout-only containers and components that draw themselves. */
    NONE,

    /** The root container of a screen (or a modal). */
    ROOT,

    BUTTON,

    // Selection
    CHECK_BOX,
    RADIO_BUTTON,
    TOGGLE_BUTTON,

    // Text
    HEADER,
    LABEL,
    PARAGRAPH,
    LIST_ITEM,
    ICON,

    // Containers
    PANEL,
    HBOX,
    VBOX,
    MODAL,

    // Input
    INPUT,
    NUMBER_INPUT,
    TEXT_AREA,
    SLIDER,
    SCROLL_BAR,
    PROGRESS_BAR,
    LOG_AREA,

    // Composite fragments: the container, and its parts with a [Part]
    ALERT,
    BADGE,
    CARD,
    WINDOW,
    CHECK_BOX_FIELD,
    INPUT_FIELD,
    PROGRESS_FIELD,
    TEXT_AREA_FIELD,

    /** A tab bar: the strip (no part), its content panel ([Part.CONTENT]) and scroll buttons ([Part.SCROLL]). */
    TAB_BAR,

    /** One tab in a tab bar; the renderer reads whether it is selected and its orientation. */
    TAB,
}
