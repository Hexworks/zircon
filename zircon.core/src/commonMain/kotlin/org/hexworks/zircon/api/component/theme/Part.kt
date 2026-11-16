package org.hexworks.zircon.api.component.theme

/**
 * Which piece of a composite fragment a component is, so a theme can style the title of a
 * card differently from a plain paragraph. [NONE] is the composite's own container, and
 * every standalone component.
 */
public enum class Part {
    NONE,
    TEXT,
    ICON,
    TITLE,
    SUBTITLE,
    BODY,
    META,
    HEADER,
    LABEL,
    VALUE,
    DESCRIPTION,
    DISMISS,
    DOT,
    ROW,
    INPUT,
    BUTTON,
    ACTIONS,
    TITLE_BAR,
    CONTROLS,
    CONTENT,
    SCROLL,
}
