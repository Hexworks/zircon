package org.codetome.zircon.api.behavior

/**
 * Represents a GUI element which can receive focus.
 */
interface Focusable {

    /**
     * Gives focus to this [Focusable].
     */
    fun focus()

}