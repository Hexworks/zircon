package org.hexworks.zircon.internal.behavior

import org.hexworks.cobalt.databinding.api.value.ObservableValue

/**
 * Represents a GUI element which can receive focus. When a GUI element
 * is focused it will receive activation events (Spacebar by default).
 * A [Focusable] needs to be [Identifiable] as well to allow easy identification
 * and lookup.
 */
interface Focusable : Identifiable {

    /**
     * Tells whether this [Focusable] has focus or not.
     */
    val hasFocus: ObservableValue<Boolean>

    /**
     * Requests focus for this [Focusable].
     */
    fun requestFocus()

    /**
     * Clears focus from this [Focusable]. Has no effect if this [Focusable] is not focused.
     */
    fun clearFocus()

    /**
     * Tells whether this GUI element accepts focus right now.
     */
    fun acceptsFocus(): Boolean

}
