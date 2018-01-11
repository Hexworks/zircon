package org.codetome.zircon.internal.behavior

import org.codetome.zircon.api.input.Input
import java.util.*

/**
 * Represents a GUI element which can receive giveFocus.
 */
interface Focusable {

    /**
     * Tells whether this GUI element accepts focus right now.
     */
    fun acceptsFocus(): Boolean

    /**
     * Gives focus to this [Focusable].
     * @param input the [Input] (if any) which triggered the focus change
     * @return true if the focus was accepted, false if not
     */
    fun giveFocus(input: Optional<Input> = Optional.empty()): Boolean

    /**
     * Takes focus away from this [Focusable].
     * @param input the [Input] (if any) which triggered the focus change
     */
    fun takeFocus(input: Optional<Input> = Optional.empty())

}