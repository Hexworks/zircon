package org.hexworks.zircon.internal.tileset.impl

import org.hexworks.zircon.api.data.ImageTile
import org.hexworks.zircon.api.tileset.TileTextureMetadata

data class GraphicTileTextureMetadata(override val x: Int,
                                      override val y: Int,
                                      override val width: Int,
                                      override val height: Int,
                                      val name: String,
                                      val tags: Set<String>) : TileTextureMetadata<ImageTile>
