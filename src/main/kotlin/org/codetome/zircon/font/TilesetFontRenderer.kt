package org.codetome.zircon.font

import org.codetome.zircon.TextCharacter

class TilesetFontRenderer<out S : Sprite<I>, out I, in T>(width: Int,
                                                          height: Int,
                                                          private val renderer: CharacterImageRenderer<I, T>,
                                                          private val sprite: S)
    : MonospaceFontRenderer<T>(width, height) {

    override fun renderCharacter(textCharacter: TextCharacter, surface: T, x: Int, y: Int) {
        val cp437Idx = TilesetResource.fetchCP437IndexForChar(textCharacter.getCharacter())
        val spriteX = cp437Idx.rem(16) * width
        val spriteY = cp437Idx.div(16) * height
        renderer.renderFromImage(sprite.getSubImage(
                spriteX,
                spriteY,
                width,
                height), surface, x, y)
    }
}
