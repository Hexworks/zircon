package org.hexworks.zircon.api.component.theme

/**
 * The semantic weight of a component, selecting the look within its [ComponentRole]. Variants
 * are semantic so they port between themes: a theme decides what `PRIMARY` looks like, and a
 * palette which colors `WARNING` gets.
 */
public enum class Variant {
    DEFAULT,

    /** The main action of a view (Save, Search). */
    PRIMARY,

    /** Lesser weight (Cancel, Clear). */
    SECONDARY,

    /** Reads like a link: text only. */
    LINK,

    // Status
    INFO,
    SUCCESS,
    WARNING,
    ERROR,
}
