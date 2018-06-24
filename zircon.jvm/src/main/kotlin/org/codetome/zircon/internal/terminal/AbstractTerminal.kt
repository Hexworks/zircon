package org.codetome.zircon.internal.terminal

import org.codetome.zircon.api.Size
import org.codetome.zircon.api.behavior.Styleable
import org.codetome.zircon.api.builder.StyleSetBuilder
import org.codetome.zircon.api.terminal.Terminal
import org.codetome.zircon.api.terminal.TerminalResizeListener
import org.codetome.zircon.internal.behavior.impl.DefaultStyleable
import java.util.concurrent.atomic.AtomicReference

abstract class AbstractTerminal(styleable: Styleable = DefaultStyleable(StyleSetBuilder.defaultStyle()))
    : Styleable by styleable, Terminal {

    private val resizeListeners = mutableListOf<TerminalResizeListener>()
    private var lastKnownSize = Size.unknown()

    /**
     * Call this method when the terminal has been resized or the initial size of the terminal
     * has been discovered. It will trigger all resize listeners,
     * but only if the size has changed from before.
     */
    protected fun onResized(newSize: Size) {
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
