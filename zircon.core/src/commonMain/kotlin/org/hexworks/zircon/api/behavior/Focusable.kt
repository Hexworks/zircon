package org.hexworks.zircon.api.behavior

import org.hexworks.cobalt.databinding.api.value.ObservableValue

/**
 * Represents a GUI element that can receive focus. When a GUI element
 * is focused, it can be activated by activation events (Space bar by default).
 * For example, pressing the spacebar when a button is focused will trigger the
 * button's action.
 *
 * A [Focusable] needs to be [Identifiable] as well to allow easy identification
 * and lookup.
 */
public interface Focusable : Identifiable {

    /**
     * Tells whether this [Focusable] has focus or not.
     */
    public val hasFocus: Boolean
    public val hasFocusProperty: ObservableValue<Boolean>

    /**
     * Requests focus for this [Focusable]. The success of the request depends on
     * whether this [Focusable] [acceptsFocus] or not
     * @return `true` if [hasFocus] `false` if not
     */
    public fun requestFocus(): Boolean

    /**
     * Clears focus from this [Focusable]. Has no effect if this [Focusable] is not focused.
     * After this operation [hasFocus] will be `false`
     */
    public fun clearFocus()

    /**
     * Tells whether this [Focusable] accepts focus right now.
     */
    public fun acceptsFocus(): Boolean

}
