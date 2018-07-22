package org.codetome.zircon.api.interop

import org.codetome.zircon.api.screen.Screen
import org.codetome.zircon.api.screen.builder.ScreenBuilder
import org.codetome.zircon.api.terminal.Terminal

object Screens {

    @JvmStatic
    fun createScreenFor(terminal: Terminal): Screen = ScreenBuilder.createScreenFor(terminal)
}
