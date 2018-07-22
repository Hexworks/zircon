package org.codetome.zircon.api.interop

import org.codetome.zircon.api.Size
import org.codetome.zircon.api.TextCharacter
import org.codetome.zircon.api.builder.graphics.LayerBuilder
import org.codetome.zircon.internal.font.impl.FontSettings

object Layers {

    @JvmStatic
    fun defaultFont() = FontSettings.NO_FONT

    @JvmStatic
    fun defaultSize() = Size.one()

    @JvmStatic
    fun defaultFiller() = TextCharacter.empty()

    @JvmStatic
    fun newBuilder() = LayerBuilder()
}
