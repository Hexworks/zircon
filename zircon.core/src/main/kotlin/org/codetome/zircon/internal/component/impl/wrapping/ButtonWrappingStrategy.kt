package org.codetome.zircon.internal.component.impl.wrapping

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.graphics.StyleSet
import org.codetome.zircon.api.graphics.TextImage
import org.codetome.zircon.internal.component.WrappingStrategy

class ButtonWrappingStrategy : WrappingStrategy {

    override fun getOccupiedSize() = Size.create(2, 0)

    override fun getOffset() = Position.create(1, 0)

    override fun apply(textImage: TextImage, size: Size, offset: Position, style: StyleSet) {
        textImage.setCharacterAt(offset, '[')
        textImage.setCharacterAt(offset.withRelativeX(size.xLength - 1), ']')
    }

    override fun isThemeNeutral() = false

}
