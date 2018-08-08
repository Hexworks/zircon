package org.codetome.zircon.internal.tileset.impl

import org.codetome.zircon.api.data.ImageTile
import org.codetome.zircon.api.tileset.TileTextureMetadata

data class GraphicTileTextureMetadata(override val x: Int,
                                      override val y: Int,
                                      override val width: Int,
                                      override val height: Int,
                                      val name: String,
                                      val tags: Set<String>) : TileTextureMetadata<ImageTile>
