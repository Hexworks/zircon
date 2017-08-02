package org.codetome.zircon.terminal

interface TerminalFactory {
    /**
     * Instantiates a [Terminal] according to the factory implementation.
     */
    fun buildTerminal(): Terminal
}
