package org.codetome.zircon.font

import org.codetome.zircon.TextCharacter
import org.codetome.zircon.tileset.DFTilesetResource

class TilesetFontRenderer<out S : Sprite<I>, out I, in T>(width: Int,
                                                          height: Int,
                                                          private val renderer: CharacterImageRenderer<I, T>,
                                                          private val sprite: S)
    : FontRenderer<T>(width, height) {

    override fun renderCharacter(textCharacter: TextCharacter, surface: T, x: Int, y: Int) {
        val cp437Idx = DFTilesetResource.fetchCP437IndexForChar(textCharacter.getCharacter())
        val spriteX = cp437Idx.rem(16) * width
        val spriteY = cp437Idx.div(16) * height
        renderer.renderFromImage(
                foregroundColor = textCharacter.getForegroundColor(),
                backgroundColor = textCharacter.getBackgroundColor(),
                image = sprite.getSubImage(
                        spriteX,
                        spriteY,
                        width,
                        height),
                surface = surface,
                x = x,
                y = y)
    }
}
