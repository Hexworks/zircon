package org.codetome.zircon.platform.factory

import org.codetome.zircon.api.color.TextColor

expect object TextColorFactory {

    fun create(red: Int, green: Int, blue: Int, alpha: Int): TextColor
}
