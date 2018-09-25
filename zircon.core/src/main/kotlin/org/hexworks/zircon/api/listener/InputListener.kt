package org.hexworks.zircon.api.listener

import org.hexworks.zircon.api.input.Input

/**
 * Listener interface for [Input]s.
 */
interface InputListener {

    fun inputEmitted(input: Input) {}
}
