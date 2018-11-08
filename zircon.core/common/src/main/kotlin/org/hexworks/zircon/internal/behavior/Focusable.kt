package org.hexworks.zircon.internal.behavior

import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.zircon.api.input.Input

/**
 * Represents a GUI element which can receive focus.
 */
interface Focusable : Identifiable {

    /**
     * Tells whether this GUI element accepts focus right now.
     */
    fun acceptsFocus(): Boolean

    /**
     * Gives focus to this [Focusable].
     * @param input the [Input] (if any) which triggered the focus change
     * @return true if the focus was accepted, false if not
     */
    fun giveFocus(input: Maybe<Input> = Maybe.empty()): Boolean

    /**
     * Takes focus away from this [Focusable].
     * @param input the [Input] (if any) which triggered the focus change
     */
    fun takeFocus(input: Maybe<Input> = Maybe.empty())

}
