package org.hexworks.zircon

import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.TileComposite

/**
 * Converts a collection of character tiles (usually found at [TileComposite.tiles]) into a string.
 */
fun TileComposite.convertCharacterTilesToString(padToSize: Size = Size.zero()): String {
    val strings = mutableListOf<StringBuilder>()
    while (strings.size < padToSize.height) {
        val sb = StringBuilder().also { sb -> repeat(padToSize.width) { sb.append(' ') } }
        strings.add(sb)
    }

    for ((position, tile) in this.tiles) {
        val (x, y) = position
        while (y >= strings.size) {
            strings.add(StringBuilder())
        }
        val sb = strings[y]
        while (sb.length <= x || sb.length < padToSize.width) {
            sb.append(' ')
        }

        sb[x] = tile.asCharacterTileOrElse { Tile.empty() }.character
    }
    return strings.joinToString(separator = "\n") { it.toString() }
}
