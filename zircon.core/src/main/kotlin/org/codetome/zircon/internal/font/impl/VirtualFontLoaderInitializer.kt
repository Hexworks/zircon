package org.codetome.zircon.internal.font.impl

import org.codetome.zircon.api.GUIBackend
import org.codetome.zircon.internal.font.FontLoaderInitializer
import org.codetome.zircon.internal.font.FontRegistry

class VirtualFontLoaderInitializer : FontLoaderInitializer{

    override fun initialize() {
        FontRegistry.registerFontLoader(GUIBackend.HEADLESS, VirtualFontLoader())
    }
}