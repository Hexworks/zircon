package org.hexworks.zircon.api.util

public fun Char.isControlCharacter(): Boolean = code !in 32..<127

public fun Char.isPrintableCharacter(): Boolean = !isControlCharacter()

public fun Char.isDigitCharacter(): Boolean = code in 48..57