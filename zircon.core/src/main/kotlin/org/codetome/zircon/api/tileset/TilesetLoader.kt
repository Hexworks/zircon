package org.codetome.zircon.api.tileset

import org.codetome.zircon.internal.tileset.MetadataPickingStrategy
import org.codetome.zircon.internal.tileset.impl.PickFirstMetaStrategy

interface TilesetLoader {

    fun fetchTiledFont(width: Int,
                       height: Int,
                       path: String,
                       cacheFonts: Boolean,
                       metadataTile: Map<Char, List<TileTextureMetadata>>,
                       metadataPickingStrategy: MetadataPickingStrategy = PickFirstMetaStrategy()) : Tileset
}
