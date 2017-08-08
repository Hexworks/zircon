package org.codetome.zircon.terminal.swing

import org.codetome.zircon.TextCharacter
import org.codetome.zircon.TextColor
import org.codetome.zircon.font.CharacterImageRenderer
import java.awt.Color
import java.awt.Font
import java.awt.Graphics
import java.awt.image.BufferedImage


class SwingCharacterImageRenderer(
        private val fontWidth: Int,
        private val fontHeight: Int)
    : CharacterImageRenderer<BufferedImage, Graphics> {

    override fun renderFromFont(textCharacter: TextCharacter, font: Font, surface: Graphics, x: Int, y: Int) {
        renderBackground(surface, textCharacter.getBackgroundColor().toAWTColor(), x, y)

        surface.color = textCharacter.getForegroundColor().toAWTColor()
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

    override fun renderFromImage(foregroundColor: TextColor,
                                 backgroundColor: TextColor,
                                 image: BufferedImage,
                                 surface: Graphics,
                                 x: Int,
                                 y: Int) {
        renderBackground(surface, backgroundColor.toAWTColor(), x, y)
        val r = foregroundColor.getRed().toFloat() / 255
        val g = foregroundColor.getGreen().toFloat() / 255
        val b = foregroundColor.getBlue().toFloat() / 255
        surface.drawImage(color(r, g, b, image), x, y, null)
    }

    private fun color(r: Float, g: Float, b: Float, src: BufferedImage): BufferedImage {
        val newImage = BufferedImage(src.width, src.height, BufferedImage.TRANSLUCENT)
        val graphics = newImage.createGraphics()
        graphics.drawImage(src, 0, 0, null)
        graphics.dispose()

        (0..newImage.width - 1).forEach { i ->
            (0..newImage.height - 1).forEach { j ->
                val ax = newImage.colorModel.getAlpha(newImage.raster.getDataElements(i, j, null))
                var rx = newImage.colorModel.getRed(newImage.raster.getDataElements(i, j, null))
                var gx = newImage.colorModel.getGreen(newImage.raster.getDataElements(i, j, null))
                var bx = newImage.colorModel.getBlue(newImage.raster.getDataElements(i, j, null))
                rx = (rx * r).toInt()
                gx = (gx * g).toInt()
                bx = (bx * b).toInt()
                newImage.setRGB(i, j, (ax shl 24) or (rx shl 16) or (gx shl 8) or (bx shl 0))
            }
        }
        return newImage
    }

    private fun renderBackground(surface: Graphics, backgroundColor: Color, x: Int, y: Int) {
        surface.color = backgroundColor
        surface.setClip(x, y, fontWidth, fontHeight)
        surface.fillRect(x, y, fontWidth, fontHeight)
    }
}