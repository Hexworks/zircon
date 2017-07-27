package org.codetome.zircon.terminal.virtual

import org.codetome.zircon.terminal.TerminalResizeListener

/**
 * Listener class for [VirtualTerminal] that allows you to receive callbacks on certain events.
 * Please note that while this extends [TerminalResizeListener] and can be attached to a
 * [VirtualTerminal] through [org.codetome.zircon.terminal.Terminal.addResizeListener],
 * in that case only the resize event will fire on the listener.
 */
interface VirtualTerminalListener : TerminalResizeListener {
    /**
     * Called when the [org.codetome.zircon.terminal.Terminal.flush] method is invoked
     * on the [VirtualTerminal].
     */
    fun onFlush()

    /**
     * Called when the [org.codetome.zircon.terminal.Terminal.close] method is invoked
     * on the [VirtualTerminal].
     */
    fun onClose()
}
