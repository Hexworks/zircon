package org.hexworks.zircon.api

import org.hexworks.zircon.api.builder.graphics.TextCharacterStringBuilder

object TextCharacterStrings {

    /**
     * Creates a new [TextCharacterStringBuilder].
     */
    @JvmStatic
    fun newBuilder() = TextCharacterStringBuilder.newBuilder()
}
