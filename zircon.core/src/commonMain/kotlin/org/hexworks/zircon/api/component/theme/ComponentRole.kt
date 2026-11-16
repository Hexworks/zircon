package org.hexworks.zircon.api.component.theme

/**
 * What a component *is* for a [ComponentTheme]. Every component carries a role; builders
 * default it to the component kind (`BUTTON`, `LABEL`, `PANEL`) and the composite fragments
 * give their parts finer roles (`ALERT_INFO_TEXT`, `BADGE_DISMISS`), so a theme can style the
 * label inside an alert differently from a plain label.
 *
 * Roles are semantic, not visual: `BUTTON_PRIMARY` rather than "double border", so a UI keeps
 * its meaning when the theme changes. A theme that has no entry for a role leaves the
 * component as it is.
 */
public enum class ComponentRole {

    /** Never themed. For layout-only containers and components that draw themselves. */
    NONE,

    /** The root container of a screen (or a modal). */
    ROOT,

    // Buttons
    /** The default button. */
    BUTTON,
    /** The one button of a view that carries the main action (Save, Search). */
    BUTTON_PRIMARY,
    /** A button of lesser weight (Cancel, Clear). */
    BUTTON_SECONDARY,
    /** A button that reads like a link: text only. */
    BUTTON_LINK,

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

    // Alert fragment: the panel per severity, and its icon + text per severity
    ALERT_INFO,
    ALERT_SUCCESS,
    ALERT_ERROR,
    ALERT_INFO_TEXT,
    ALERT_SUCCESS_TEXT,
    ALERT_ERROR_TEXT,

    // Badge fragment
    BADGE,
    BADGE_TEXT,
    BADGE_DOT_SUCCESS,
    BADGE_DOT_ERROR,
    BADGE_DISMISS,

    // Card fragment
    CARD,
    CARD_META,
    CARD_ICON,
    CARD_SUBTITLE,
    CARD_TITLE,
    CARD_BODY,

    // Window fragment
    WINDOW,
    WINDOW_TITLE_BAR,
    WINDOW_TITLE,
    WINDOW_CONTROLS,
    WINDOW_HEADER,
    WINDOW_BODY,

    // Check box field fragment
    CHECK_BOX_FIELD,
    CHECK_BOX_DESCRIPTION,

    // Input field fragment: label above, a framed row holding the bare input and an icon or button
    INPUT_FIELD,
    INPUT_FIELD_LABEL,
    INPUT_FIELD_ROW,
    INPUT_FIELD_INPUT,
    INPUT_FIELD_ICON,
    INPUT_FIELD_BUTTON,

    // Progress field fragment
    PROGRESS_FIELD,
    PROGRESS_FIELD_HEADER,
    PROGRESS_FIELD_LABEL,
    PROGRESS_FIELD_VALUE,

    // Text area field fragment
    TEXT_AREA_FIELD,
    TEXT_AREA_FIELD_LABEL,
    TEXT_AREA_FIELD_ACTIONS,

    // Tab bars: the strip, an unselected tab (button), the selected tab (label), the content panel
    TAB_BAR,
    TAB_CONTENT,
    TAB,
    TAB_SELECTED,
    TAB_SCROLL,
    TAB_BAR_VERTICAL,
    TAB_VERTICAL,
    TAB_VERTICAL_SELECTED,
}
