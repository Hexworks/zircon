package org.codetome.zircon.api.behavior

/**
 * Represents a GUI element which can receive giveFocus.
 */
interface Focusable {

    fun acceptsFocus(): Boolean

    /**
     * Gives focus to this [Focusable].
     * @return true if the focus was accepted, false if not
     */
    fun giveFocus(): Boolean

    /**
     * Takes focus away from this [Focusable].
     */
    fun takeFocus()

}