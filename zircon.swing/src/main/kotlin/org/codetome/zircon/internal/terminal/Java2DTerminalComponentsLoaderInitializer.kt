package org.codetome.zircon.internal.terminal

import org.codetome.zircon.api.GUIBackend

class Java2DTerminalComponentsLoaderInitializer : TerminalComponentsLoaderInitializer {
    override fun initialize() {
        TerminalComponentsRegistry.registerComponentLoader(GUIBackend.SWING, Java2DTerminalComponentsLoader())
        TerminalComponentsRegistry.selectBackend(GUIBackend.SWING)
    }
}
