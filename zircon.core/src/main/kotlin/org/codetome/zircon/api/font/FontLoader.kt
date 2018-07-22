package org.codetome.zircon.api.font

import org.codetome.zircon.internal.font.MetadataPickingStrategy
import org.codetome.zircon.internal.font.impl.PickFirstMetaStrategy

interface FontLoader {

    fun fetchPhysicalFont(size: Float = 18f,
                          path: String,
                          cacheFonts: Boolean = true,
                          withAntiAlias: Boolean = true): Font

    fun fetchTiledFont(width: Int,
                       height: Int,
                       path: String,
                       cacheFonts: Boolean,
                       metadata: Map<Char, List<CharacterMetadata>>,
                       metadataPickingStrategy: MetadataPickingStrategy = PickFirstMetaStrategy()) : Font
}
