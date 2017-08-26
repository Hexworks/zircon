package org.codetome.zircon.internal.component.impl

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.graphics.StyleSet
import org.codetome.zircon.api.graphics.TextImage
import org.codetome.zircon.internal.component.WrappingStrategy

class ButtonWrappingStrategy : WrappingStrategy {

    override fun getOccupiedSize() = Size.of(2, 0)

    override fun getOffset() = Position.of(1, 0)

    override fun apply(textImage: TextImage, size: Size, style: StyleSet) {
        textImage.setCharacterAt(Position.DEFAULT_POSITION, '[')
        textImage.setCharacterAt(Position.DEFAULT_POSITION.withRelativeColumn(textImage.getBoundableSize().columns - 1), ']')
    }

    override fun isThemeNeutral() = false

}