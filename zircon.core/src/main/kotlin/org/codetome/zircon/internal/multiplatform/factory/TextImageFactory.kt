package org.codetome.zircon.internal.multiplatform.factory

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.TextCharacter
import org.codetome.zircon.api.graphics.TextImage

expect object TextImageFactory {

    fun create(size: Size,
               filler: TextCharacter,
               chars: MutableMap<Position, TextCharacter>): TextImage
}
