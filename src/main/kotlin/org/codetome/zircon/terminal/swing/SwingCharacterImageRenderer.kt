package org.codetome.zircon.terminal.swing

import org.codetome.zircon.TextCharacter
import org.codetome.zircon.font.CharacterImageRenderer
import java.awt.Font
import java.awt.Graphics
import java.awt.Image

class SwingCharacterImageRenderer(
        private val fontWidth: Int,
        private val fontHeight: Int)
    : CharacterImageRenderer<Image, Graphics> {

    override fun renderFromFont(textCharacter: TextCharacter, font: Font, surface: Graphics, x: Int, y: Int) {
        surface.font = font
        val fontMetrics = surface.fontMetrics
        surface.drawString(Character.toString(textCharacter.getCharacter()),
                x,
                y + fontHeight - fontMetrics.descent + 1)


        if (textCharacter.isCrossedOut()) {
            val lineStartX = x
            val lineStartY = y + fontHeight / 2
            val lineEndX = lineStartX + fontWidth
            surface.drawLine(lineStartX, lineStartY, lineEndX, lineStartY)
        }
        if (textCharacter.isUnderlined()) {
            val lineStartX = x
            val lineStartY = y + fontHeight - fontMetrics.descent + 1
            val lineEndX = lineStartX + fontWidth
            surface.drawLine(lineStartX, lineStartY, lineEndX, lineStartY)
        }
    }

    override fun renderFromImage(image: Image, surface: Graphics, x: Int, y: Int) {
        surface.drawImage(image, x, y, null)
    }
}