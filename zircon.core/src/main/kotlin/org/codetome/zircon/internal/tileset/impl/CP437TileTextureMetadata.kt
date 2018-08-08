package org.codetome.zircon.internal.tileset.impl

import org.codetome.zircon.api.data.CharacterTile
import org.codetome.zircon.api.tileset.TileTextureMetadata

class CP437TileTextureMetadata(
        override val x: Int,
        override val y: Int,
        override val width: Int,
        override val height: Int,
        val character: Char) : TileTextureMetadata<CharacterTile>
