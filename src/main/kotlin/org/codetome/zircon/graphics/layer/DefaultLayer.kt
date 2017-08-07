package org.codetome.zircon.graphics.layer

import org.codetome.zircon.Position
import org.codetome.zircon.TextCharacter
import org.codetome.zircon.behavior.Boundable
import org.codetome.zircon.behavior.impl.DefaultBoundable
import org.codetome.zircon.builder.TextImageBuilder
import org.codetome.zircon.graphics.TextImage
import org.codetome.zircon.terminal.Size

class DefaultLayer private constructor(private val textImage: TextImage,
                                       private val boundable: Boundable)
    : Layer, TextImage by textImage, Boundable by boundable {

    constructor(size: Size, filler: TextCharacter, offset: Position)
            : this(
            textImage = TextImageBuilder.newBuilder()
                    .size(size)
                    .filler(filler)
                    .build(),
            boundable = DefaultBoundable(offset, size))

    override fun getSize() = textImage.getSize()

    override fun getCharacterAt(position: Position) = textImage.getCharacterAt(position - boundable.getOffset())

    override fun setCharacterAt(position: Position, character: TextCharacter) {
        textImage.setCharacterAt(position - boundable.getOffset(), character)
    }
}