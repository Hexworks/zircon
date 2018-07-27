package org.codetome.zircon.internal.tileset

import org.codetome.zircon.api.tileset.TileTextureMetadata

data class DefaultTileTextureMetadata(override val char: Char,
                                      override val tags: Set<String> = setOf(),
                                      override val x: Int,
                                      override val y: Int) : TileTextureMetadata
