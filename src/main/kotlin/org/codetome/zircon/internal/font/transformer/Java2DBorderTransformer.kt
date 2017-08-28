package org.codetome.zircon.internal.font.transformer

import org.codetome.zircon.api.Modifiers.BorderPosition.*
import org.codetome.zircon.api.TextCharacter
import org.codetome.zircon.internal.font.FontRegionTransformer
import java.awt.Graphics
import java.awt.image.BufferedImage

class Java2DBorderTransformer : FontRegionTransformer<BufferedImage> {

    override fun transform(region: BufferedImage, textCharacter: TextCharacter): BufferedImage {
        return region.also {
            it.graphics.apply {
                color = textCharacter.getForegroundColor().toAWTColor()
                if (textCharacter.hasBorder()) {
                    textCharacter.fetchBorderData().let { border ->
                        border.borderPositions.forEach { pos ->
                            FILLER_LOOKUP[pos]?.invoke(region, this)
                        }
                    }
                }
                dispose()
            }
        }
    }

    companion object {
        private val FILLER_LOOKUP = mapOf(
                Pair(TOP, { region: BufferedImage, graphics: Graphics ->
                    graphics.fillRect(0, 0, region.width, 2)
                }),
                Pair(BOTTOM, { region: BufferedImage, graphics: Graphics ->
                    graphics.fillRect(0, region.height - 2, region.width, region.height)
                }),
                Pair(LEFT, { region: BufferedImage, graphics: Graphics ->
                    graphics.fillRect(0, 0, 2, region.height)
                }),
                Pair(RIGHT, { region: BufferedImage, graphics: Graphics ->
                    graphics.fillRect(region.width - 2, 0, region.width, region.height)
                }))
                .toMap()
    }
}