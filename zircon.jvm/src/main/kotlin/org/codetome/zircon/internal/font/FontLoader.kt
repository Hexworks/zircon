package org.codetome.zircon.internal.font

import org.codetome.zircon.api.font.CharacterMetadata
import org.codetome.zircon.api.font.Font
import org.codetome.zircon.internal.font.impl.PickFirstMetaStrategy
import java.io.InputStream

interface FontLoader {

    fun fetchPhysicalFont(size: Float = 18f,
                          source: InputStream,
                          cacheFonts: Boolean = true,
                          withAntiAlias: Boolean = true): Font

    fun fetchTiledFont(width: Int,
                       height: Int,
                       source: InputStream,
                       cacheFonts: Boolean,
                       metadata: Map<Char, List<CharacterMetadata>>,
                       metadataPickingStrategy: MetadataPickingStrategy = PickFirstMetaStrategy()) : Font
}