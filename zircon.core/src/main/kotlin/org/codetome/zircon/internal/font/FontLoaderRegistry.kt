package org.codetome.zircon.internal.font

import java.util.*

object FontLoaderRegistry {

    private var currentFontLoader: Optional<FontLoader> = Optional.empty()

    fun getCurrentFontLoader() = currentFontLoader.get()

    fun setFontLoader(fontLoader: FontLoader) {
        currentFontLoader = Optional.of(fontLoader)
    }
}
