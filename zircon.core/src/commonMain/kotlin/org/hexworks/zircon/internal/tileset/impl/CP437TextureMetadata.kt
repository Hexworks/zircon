package org.hexworks.zircon.internal.tileset.impl

import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.tileset.TextureMetadata

/**
 * [TextureMetadata] implementation for CP437 textures. Augments the
 * [TextureMetadata] interface with [character] as all CP437 textures
 * will correspond to a [character].
 */
data class CP437TextureMetadata(
    override val x: Int,
    override val y: Int,
    override val width: Int,
    override val height: Int,
    val character: Char
) : TextureMetadata<CharacterTile>
