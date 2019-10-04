package org.hexworks.zircon.internal.behavior

/**
 * Represents a GUI element which can receive focus.
 */
interface Focusable : Identifiable {

    /**
     * Tells whether this GUI element accepts focus right now.
     */
    fun acceptsFocus(): Boolean

}
