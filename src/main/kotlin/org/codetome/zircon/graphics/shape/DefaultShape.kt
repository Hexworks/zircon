package org.codetome.zircon.graphics.shape

import org.codetome.zircon.Position
import org.codetome.zircon.TextCharacter
import org.codetome.zircon.graphics.TextImage

class DefaultShape(private val positions: Set<Position> = setOf())
    : Shape, Collection<Position> by positions {

    override fun getPositions() = positions

    override fun toTextImage(textCharacter: TextCharacter): TextImage {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}