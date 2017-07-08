package org.codetome.zircon.terminal

interface TerminalResizeListener {

    /**
     * The terminal has changed its size, most likely because the user has resized the window.
     */
    fun onResized(terminal: Terminal, newSize: TerminalSize)
}