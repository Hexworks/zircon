package org.codetome.zircon.internal.font

import org.codetome.zircon.internal.font.impl.VirtualFontLoader

object FontLoaderRegistry {

    private var currentFontLoader: FontLoader = VirtualFontLoader()

    fun getCurrentFontLoader() = currentFontLoader

    fun setFontLoader(fontLoader: FontLoader) {
        currentFontLoader = fontLoader
    }
}
