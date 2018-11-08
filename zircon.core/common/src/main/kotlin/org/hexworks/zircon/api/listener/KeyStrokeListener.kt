package org.hexworks.zircon.api.listener

import org.hexworks.zircon.api.input.KeyStroke

/**
 * Listener interface for [KeyStroke]s.
 */
interface KeyStrokeListener {

    fun keyStroked(keyStroke: KeyStroke) {}
}
