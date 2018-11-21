package org.hexworks.zircon.api

import org.hexworks.zircon.api.builder.graphics.CharacterTileStringBuilder
import kotlin.jvm.JvmStatic

object CharacterTileStrings {

    /**
     * Creates a new [CharacterTileStringBuilder].
     */
    @JvmStatic
    fun newBuilder() = CharacterTileStringBuilder.newBuilder()
}
