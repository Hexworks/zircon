package org.hexworks.zircon.internal.tileset.impl

import org.hexworks.zircon.api.data.tile.CharacterTile
import org.hexworks.zircon.api.tileset.TextureMetadata

data class CP437TextureMetadata(
        override val x: Int,
        override val y: Int,
        override val width: Int,
        override val height: Int,
        val character: Char) : TextureMetadata<CharacterTile>
