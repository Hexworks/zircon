package org.codetome.zircon.platform.factory

import org.codetome.zircon.api.Modifier
import org.codetome.zircon.api.color.TextColor
import org.codetome.zircon.api.graphics.StyleSet

expect object StyleSetFactory {

    fun create(foregroundColor: TextColor, backgroundColor: TextColor, modifiers: Set<Modifier>): StyleSet
}
