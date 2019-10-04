package org.hexworks.zircon

import org.hexworks.zircon.api.graphics.TileComposite

fun TileComposite.fetchCharacters(): List<Char> {
    return size.fetchPositions().map {
        getTileAt(it).get().asCharacterTile().get().character
    }
}
