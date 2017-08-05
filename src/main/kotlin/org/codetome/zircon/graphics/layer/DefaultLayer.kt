package org.codetome.zircon.graphics.layer

import org.codetome.zircon.TextCharacter
import org.codetome.zircon.builder.TextImageBuilder
import org.codetome.zircon.graphics.TextImage
import org.codetome.zircon.terminal.Size

class DefaultLayer(private val textImage: TextImage,
                   private var offset: Size) : Layer, TextImage by textImage {

    override fun getOffset() = offset

    override fun setOffset(offset: Size) {
        this.offset = offset
    }

    constructor(size: Size, filler: TextCharacter, offset: Size)
            : this(TextImageBuilder.newBuilder()
            .size(size)
            .filler(filler)
            .build(), offset)
}