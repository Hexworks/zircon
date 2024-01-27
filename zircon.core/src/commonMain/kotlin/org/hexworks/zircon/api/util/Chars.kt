package org.hexworks.zircon.api.util

fun Char.isControlCharacter() = code < 32 || code >= 127

fun Char.isPrintableCharacter() = !isControlCharacter()

fun Char.isDigitCharacter() = code in 48..57