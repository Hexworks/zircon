package org.codetome.zircon.internal.behavior.impl

import org.codetome.zircon.api.behavior.TilesetOverride
import org.codetome.zircon.api.tileset.Tileset
import org.codetome.zircon.internal.tileset.impl.FontSettings

class DefaultTilesetOverride(initialTileset: Tileset) : TilesetOverride {

    private var font = initialTileset

    override fun resetFont() {
        font = FontSettings.NO_FONT
    }

    override fun getCurrentFont(): Tileset = font

    override fun useFont(tileset: Tileset): Boolean {
        val currentFont = getCurrentFont()
        if (currentFont !== FontSettings.NO_FONT) {
            require(currentFont.getSize() == tileset.getSize()) {
                "Can't override previous tileset with size: ${getCurrentFont().getSize()} with a Tileset with" +
                        " different size: ${tileset.getSize()}"
            }
        }
        this.font = tileset
        return true
    }
}
