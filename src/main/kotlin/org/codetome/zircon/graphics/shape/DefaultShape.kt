package org.codetome.zircon.graphics.shape

import org.codetome.zircon.Position
import org.codetome.zircon.Size
import org.codetome.zircon.TextCharacter
import org.codetome.zircon.api.TextCharacterBuilder
import org.codetome.zircon.api.TextImageBuilder
import org.codetome.zircon.graphics.DefaultTextImage
import org.codetome.zircon.graphics.TextImage

class DefaultShape(private val positions: Set<Position> = setOf())
    : Shape, Collection<Position> by positions {

    override fun getPositions() = positions

    override fun toTextImage(textCharacter: TextCharacter): TextImage {
        var minCol = Int.MAX_VALUE
        var minRow = Int.MAX_VALUE
        var maxCol = Int.MIN_VALUE
        var maxRow = Int.MIN_VALUE
        positions.forEach { (col, row) ->
            minCol = Math.min(minCol, col)
            maxCol = Math.max(maxCol, col)
            minRow = Math.min(minRow, row)
            maxRow = Math.max(maxRow, row)
        }
        val result = TextImageBuilder.newBuilder()
                .size(Size.of(maxCol - minCol, maxRow - minRow))
                .filler(TextCharacterBuilder.EMPTY)
                .build()
        positions.forEach {
            result.setCharacterAt(it, textCharacter)
        }
        return result
    }

}