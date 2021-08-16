package org.hexworks.zircon.api.util

object TextUtils {

    fun isControlCharacter(c: Char) = c.code < 32 || c.code >= 127

    fun isPrintableCharacter(c: Char) = !isControlCharacter(c)

    fun isDigitCharacter(c: Char) = c.code in 48..57
}
