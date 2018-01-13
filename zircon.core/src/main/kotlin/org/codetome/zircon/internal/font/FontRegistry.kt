package org.codetome.zircon.internal.font

import org.codetome.zircon.api.GUIBackend
import org.codetome.zircon.api.font.CharacterMetadata
import java.io.InputStream

object FontRegistry {

    private var selectedBackend = GUIBackend.HEADLESS
    private val fontLoaders = mutableMapOf<GUIBackend, FontLoader>()

    init {
        FontLoaderInitializer.initializeAll()
    }

    fun fetchPhysicalFont(size: Float,
                          source: InputStream,
                          cacheFonts: Boolean = true,
                          withAntiAlias: Boolean = true) = fontLoaders[selectedBackend]!!.fetchPhysicalFont(
            size = size,
            withAntiAlias = withAntiAlias,
            source = source,
            cacheFonts = cacheFonts)

    fun fetchTiledFont(width: Int,
                       height: Int,
                       source: InputStream,
                       cacheFonts: Boolean,
                       metadata: Map<Char, List<CharacterMetadata>>,
                       metadataPickingStrategy: MetadataPickingStrategy) = fontLoaders[selectedBackend]!!.fetchTiledFont(
            width = width,
            height = height,
            source = source,
            cacheFonts = cacheFonts,
            metadata = metadata,
            metadataPickingStrategy = metadataPickingStrategy)

    fun selectBackend(backend: GUIBackend) {
        require(selectedBackend == GUIBackend.HEADLESS) {
            "A GUI backend is already selected (${selectedBackend.name})! Did you include more than one GUI implementations in your project?"
        }
        selectedBackend = backend
    }

    fun registerFontLoader(guiBackend: GUIBackend, fontLoader: FontLoader) {
        fontLoaders[guiBackend] = fontLoader
    }
}