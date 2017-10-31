package org.codetome.zircon.api.beta.component

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.TextCharacter
import org.codetome.zircon.api.behavior.DrawSurface
import org.codetome.zircon.api.graphics.TextImage
import java.util.*

class TextImageGameArea(private val backend: TextImage)
    : GameArea, DrawSurface by backend {

    override fun getSize() = backend.getBoundableSize()

    override fun getCharacterAt(position: Position): Optional<TextCharacter> {
        return backend.getCharacterAt(position)
    }

    override fun getSegment(offset: Position, size: Size) =
            backend.fetchCellsBy(offset, size)

    override fun getSegmentImage(offset: Position, size: Size) =
            backend.toSubImage(offset, size)
}