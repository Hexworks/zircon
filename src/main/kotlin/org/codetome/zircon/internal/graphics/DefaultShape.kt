package org.codetome.zircon.internal.graphics

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.TextCharacter
import org.codetome.zircon.api.builder.TextCharacterBuilder
import org.codetome.zircon.api.builder.TextImageBuilder
import org.codetome.zircon.api.graphics.Shape
import org.codetome.zircon.api.graphics.TextImage

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
