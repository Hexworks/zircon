package org.hexworks.zircon.api.behavior

import org.hexworks.zircon.api.input.Input
import org.hexworks.zircon.api.util.Consumer

/**
 * Represents an object which (re) emits the [Input]s
 * it has received from the underlying technology (like Swing or LibGDX).
 */
interface InputEmitter {

    /**
     * Adds an input listener to this [InputEmitter].
     * It will be notified when an [Input] is consumed
     * by this object.
     */
    fun onInput(listener: Consumer<Input>)

    /**
     * Adds an input listener to this [InputEmitter].
     * It will be notified when an [Input] is consumed
     * by this object.
     */
    fun onInput(listener: (Input) -> Unit) = onInput(object : Consumer<Input> {
        override fun accept(p: Input) {
            listener.invoke(p)
        }
    })
}
