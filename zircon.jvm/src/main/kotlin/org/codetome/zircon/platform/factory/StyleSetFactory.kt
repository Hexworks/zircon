package org.codetome.zircon.platform.factory

import org.codetome.zircon.api.Modifier
import org.codetome.zircon.api.color.TextColor
import org.codetome.zircon.api.graphics.StyleSet
import org.codetome.zircon.internal.graphics.DefaultStyleSet

actual object StyleSetFactory {
    actual fun create(foregroundColor: TextColor, backgroundColor: TextColor, modifiers: Set<Modifier>): StyleSet {
        return DefaultStyleSet(foregroundColor, backgroundColor, modifiers)
    }
}
