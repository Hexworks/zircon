package org.codetome.zircon.api.builder

import org.codetome.zircon.api.screen.Screen
import org.codetome.zircon.api.terminal.Terminal
import org.codetome.zircon.internal.screen.TerminalScreen
import org.codetome.zircon.internal.terminal.InternalTerminal

class ScreenBuilder {

    companion object {

        @JvmStatic
        fun createScreenFor(terminal: Terminal): Screen {
            return TerminalScreen(
                    terminal = terminal as InternalTerminal)
        }
    }
}
