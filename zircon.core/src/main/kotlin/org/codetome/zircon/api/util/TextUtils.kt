package org.codetome.zircon.api.util

object TextUtils {

    fun isControlCharacter(c: Char) = c.toInt() < 32 || c.toInt() == 127

    fun isPrintableCharacter(c: Char) = !isControlCharacter(c)

}