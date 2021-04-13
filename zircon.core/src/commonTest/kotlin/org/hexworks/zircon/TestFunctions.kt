package org.hexworks.zircon

import org.hexworks.zircon.api.graphics.TileComposite

fun TileComposite.fetchCharacters(): List<Char> {
    return size.fetchPositions().map {
        getTileAt(it).get().asCharacterTile().get().character
    }
}

/**
 * Ensure each line is at least [length] long, padding it with [padChar] if it's not. This splits the string on newline
 * internally and uses [lineSeparator] to re-join the lines.
 */
fun String.padLineEnd(length: Int, padChar: Char = ' ', lineSeparator: String = "\n"): String =
    lineSequence().joinToString(lineSeparator) { it.padEnd(length, padChar) }
