package org.codetome.zircon.terminal

import org.codetome.zircon.graphics.style.DefaultStyleSet

abstract class AbstractTerminal : DefaultStyleSet(), Terminal {

    private val resizeListeners = mutableListOf<TerminalResizeListener>()
    private var lastKnownSize = TerminalSize.UNKNOWN

    /**
     * Call this method when the terminal has been resized or the initial size of the terminal
     * has been discovered. It will trigger all resize listeners,
     * but only if the size has changed from before.
     */
    @Synchronized
    protected fun onResized(newSize: TerminalSize) {
        if (lastKnownSize != newSize) {
            lastKnownSize = newSize
            resizeListeners.forEach { it.onResized(this, lastKnownSize) }
        }
    }

    override fun addResizeListener(listener: TerminalResizeListener) {
        resizeListeners.add(listener)
    }

    override fun removeResizeListener(listener: TerminalResizeListener) {
        resizeListeners.remove(listener)
    }
}
