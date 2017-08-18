package org.codetome.zircon.graphics.shape

import org.codetome.zircon.Position
import org.codetome.zircon.Size
import org.codetome.zircon.TextCharacter
import org.codetome.zircon.api.TextCharacterBuilder
import org.codetome.zircon.api.TextImageBuilder
import org.codetome.zircon.graphics.image.TextImage

class DefaultShape(private val positions: Set<Position> = setOf())
    : Shape, Collection<Position> by positions {

    override fun getPositions() = positions

    override fun toTextImage(textCharacter: TextCharacter): TextImage {
        val offsetPositions = offsetToDefaultPosition()
        var maxCol = Int.MIN_VALUE
        var maxRow = Int.MIN_VALUE
        offsetPositions.forEach { (col, row) ->
            maxCol = Math.max(maxCol, col)
            maxRow = Math.max(maxRow, row)
        }
        val result = TextImageBuilder.newBuilder()
                .size(Size.of(maxCol + 1, maxRow + 1))
                .filler(TextCharacterBuilder.EMPTY)
                .build()
        offsetPositions.forEach {
            result.setCharacterAt(it, textCharacter)
        }
        return result
    }

    override fun offsetToDefaultPosition(): Shape {
        require(positions.isNotEmpty()) {
            "You can't transform a Shape with zero points!"
        }
        return DefaultShape(positions.minBy { it }!!.let { topLeft ->
            positions.map { it - topLeft }
        }.toSet())
    }
}
