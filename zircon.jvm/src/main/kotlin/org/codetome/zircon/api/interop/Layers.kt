package org.codetome.zircon.api.interop

import org.codetome.zircon.api.Size
import org.codetome.zircon.api.builder.LayerBuilder
import org.codetome.zircon.api.builder.TextCharacterBuilder
import org.codetome.zircon.internal.font.impl.FontSettings

object Layers {

    @JvmField
    val DEFAULT_FONT = FontSettings.NO_FONT

    @JvmField
    val DEFAULT_SIZE = Size.one()

    @JvmField
    val DEFAULT_FILLER = TextCharacterBuilder.empty()

    @JvmStatic
    fun newBuilder() = LayerBuilder()
}
