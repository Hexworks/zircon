package org.codetome.zircon.internal.component.impl

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.builder.BoxBuilder
import org.codetome.zircon.api.graphics.StyleSet
import org.codetome.zircon.api.graphics.TextImage
import org.codetome.zircon.internal.component.WrappingStrategy
import org.codetome.zircon.internal.graphics.BoxType

    class BorderWrappingStrategy : WrappingStrategy {

        override fun getOccupiedSize() = Size.of(0, 0)

        override fun getOffset() = Position.DEFAULT_POSITION

        override fun apply(textImage: TextImage, size: Size, style: StyleSet) {
            // TODO: apply borders to chars
        }

        override fun isThemeNeutral() = false

    }