package org.codetome.zircon.internal.component.impl.wrapping

import org.codetome.zircon.api.data.Position
import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.graphics.StyleSet
import org.codetome.zircon.api.graphics.TileImage
import org.codetome.zircon.internal.component.WrappingStrategy

class ButtonWrappingStrategy : WrappingStrategy {

    override fun getOccupiedSize() = Size.create(2, 0)

    override fun getOffset() = Position.create(1, 0)

    override fun apply(tileImage: TileImage, size: Size, offset: Position, style: StyleSet) {
        tileImage.setCharAt(offset, '[')
        tileImage.setCharAt(offset.withRelativeX(size.xLength - 1), ']')
    }

    override fun isThemeNeutral() = false

}
