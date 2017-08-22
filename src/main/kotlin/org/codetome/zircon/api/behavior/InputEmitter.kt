package org.codetome.zircon.api.behavior

import org.codetome.zircon.api.input.Input
import java.util.function.Consumer

/**
 * Represents an object which emits its [Input]s.
 */
interface InputEmitter {

    /**
     * Adds an input listener to this [InputEmitter].
     * It will be notified when an [Input] is received
     * by this object.
     */
    fun addInputListener(listener: Consumer<Input>)
}