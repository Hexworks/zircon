package org.codetome.zircon.platform.factory

import org.codetome.zircon.api.color.TextColor
import org.codetome.zircon.internal.color.DefaultTextColor

/**
 * Use this factory to create [TextColor]s.
 */
actual object TextColorFactory {

    actual fun create(red: Int, green: Int, blue: Int, alpha: Int): TextColor {
        return DefaultTextColor(red, green, blue, alpha)
    }

}
