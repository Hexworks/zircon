package org.codetome.zircon.api.terminal

import org.codetome.zircon.api.Size
import org.codetome.zircon.api.terminal.Terminal

interface TerminalResizeListener {

    /**
     * The terminal has changed its size, most likely because the user has resized the window.
     */
    fun onResized(terminal: Terminal, newSize: Size) {}
}