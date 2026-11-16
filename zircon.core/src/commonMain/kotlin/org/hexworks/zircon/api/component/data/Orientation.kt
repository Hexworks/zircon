package org.hexworks.zircon.api.component.data

/**
 * Which way a component runs: a tab bar's strip, a slider, a scroll bar. Theme renderers
 * read it to draw along the right axis.
 */
public enum class Orientation {
    HORIZONTAL,
    VERTICAL,
}
