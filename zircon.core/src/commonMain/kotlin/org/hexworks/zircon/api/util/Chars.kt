package org.hexworks.zircon.api.util

fun Char.isControlCharacter() = code !in 32..<127

fun Char.isPrintableCharacter() = !isControlCharacter()

fun Char.isDigitCharacter() = code in 48..57