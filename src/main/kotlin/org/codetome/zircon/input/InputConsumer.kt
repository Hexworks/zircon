package org.codetome.zircon.input

/**
 * An [InputConsumer] is an object which is capable of
 * handling [Input]s (actions on the user interface)
 */
interface InputConsumer {

    /**
     * Adds a [Input] to the input queue of this [InputConsumer].
     */
    fun addInput(input: Input)
}