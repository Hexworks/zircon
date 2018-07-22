package org.codetome.zircon.internal.font.impl

import org.codetome.zircon.api.font.FontLoader

object FontLoaderRegistry {

    private var currentFontLoader: FontLoader = VirtualFontLoader()

    fun getCurrentFontLoader() = currentFontLoader

    fun setFontLoader(fontLoader: FontLoader) {
        currentFontLoader = fontLoader
    }
}
