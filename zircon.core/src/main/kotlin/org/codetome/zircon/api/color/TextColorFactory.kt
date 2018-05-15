package org.codetome.zircon.api.color

expect object TextColorFactory : TextColorCompanion {

    fun create(red: Int, green: Int, blue: Int, alpha: Int): TextColor
}
