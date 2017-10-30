package org.codetome.zircon.api.beta.component

import org.codetome.zircon.api.Cell
import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.TextCharacter
import java.util.*

interface GameArea {

    fun getSize(): Size

    fun getCharacterAt(position: Position): Optional<TextCharacter>

    fun getSegment(offset: Position, size: Size): Iterable<Cell>
}