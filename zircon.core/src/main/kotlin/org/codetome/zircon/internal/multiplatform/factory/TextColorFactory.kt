package org.codetome.zircon.internal.multiplatform.factory

import org.codetome.zircon.api.color.TextColor
import org.codetome.zircon.api.color.TextColorCompanion

expect object TextColorFactory : TextColorCompanion {

    fun create(red: Int, green: Int, blue: Int, alpha: Int): TextColor
}
