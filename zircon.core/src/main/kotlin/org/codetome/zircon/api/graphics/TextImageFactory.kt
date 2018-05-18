package org.codetome.zircon.api.graphics

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.TextCharacter

expect object TextImageFactory {

    fun create(size: Size,
               filler: TextCharacter,
               chars: MutableMap<Position, TextCharacter>): TextImage
}
