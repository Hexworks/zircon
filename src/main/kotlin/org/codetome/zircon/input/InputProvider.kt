package org.codetome.zircon.input

import java.util.*

/**
 * Objects implementing this interface can read character streams and transform them into [Input] objects which can
 * be read in a FIFO manner.
 */
interface InputProvider {
    /**
     * Returns the next [Input] off the input queue or an empty optional if there are no more inputs
     * available at the moment.
     */
    fun pollInput(): Optional<Input>

    /**
     * Returns the next [Input] off the input queue or blocks until one is available.
     */
    fun readInput(): Input

}