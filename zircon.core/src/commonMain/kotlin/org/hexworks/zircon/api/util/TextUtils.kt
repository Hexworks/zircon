package org.hexworks.zircon.api.util

object TextUtils {

    fun isControlCharacter(c: Char) = c.toInt() < 32 || c.toInt() >= 127

    fun isPrintableCharacter(c: Char) = !isControlCharacter(c)

    fun isDigitCharacter(c: Char) = c.toInt() in 48..57
}
