package org.codetome.zircon

object TextUtils {

    fun isControlCharacter(c: Char) = c.toInt() < 32 || c.toInt() == 127

    fun isPrintableCharacter(c: Char) = !isControlCharacter(c) || c == '\t' || c == '\n' || c == '\b'

}