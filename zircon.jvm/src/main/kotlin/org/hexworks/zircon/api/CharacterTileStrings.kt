package org.hexworks.zircon.api

import org.hexworks.zircon.api.builder.graphics.CharacterTileStringBuilder

object CharacterTileStrings {

    /**
     * Creates a new [CharacterTileStringBuilder].
     */
    @JvmStatic
    fun newBuilder() = CharacterTileStringBuilder.newBuilder()
}
